/**
 * 
 */
package com.twolak.springframework.spring5mvcrest.controllers.v1;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.twolak.springframework.spring5mvcrest.api.v1.model.VendorDTO;
import com.twolak.springframework.spring5mvcrest.api.v1.model.VendorListDTO;
import com.twolak.springframework.spring5mvcrest.services.ResourceNotFoundException;
import com.twolak.springframework.spring5mvcrest.services.VendorService;

import static com.twolak.springframework.spring5mvcrest.controllers.v1.AbstractRestControllerTest.asJsonString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;

/**
 * @author twolak
 *
 */
@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = {VendorController.class})
public class VendorControllerIT {
	
	private static final String VENDOR_URL_PREFIX = VendorController.BASE_URL + "/";
	private static final Long VEND_ID_1 = 1L;
	private static final Long VEND_ID_2 = 2L;
	private static final String NAME_1 = "Vendor 1";
	private static final String NAME_2 = "Vendor 2";
	
	@MockBean
	private VendorService vendorService;
	
	@Autowired
	private MockMvc mockMvc;
	
	private VendorDTO vendorDTO_1;
	private VendorDTO vendorDTO_2;
	
	@BeforeEach
	void setUp() throws Exception {
		vendorDTO_1 = new VendorDTO();
		vendorDTO_1.setName(NAME_1);
		vendorDTO_1.setVendorUrl(VENDOR_URL_PREFIX + VEND_ID_1);
		vendorDTO_2 = new VendorDTO();
		vendorDTO_2.setName(NAME_2);
		vendorDTO_2.setVendorUrl(VENDOR_URL_PREFIX + VEND_ID_2);
	}
	
	@Test
	public void testListAllVendors() throws Exception {
		VendorListDTO vendorListDTO = new VendorListDTO(Arrays.asList(this.vendorDTO_1, this.vendorDTO_2));
		given(this.vendorService.getAllVendors()).willReturn(vendorListDTO);
		
		this.mockMvc.perform(get(VENDOR_URL_PREFIX)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.vendors", hasSize(2)));
		then(this.vendorService).should(times(1)).getAllVendors();
		then(this.vendorService).shouldHaveNoMoreInteractions();
	}
	
	@Test
	public void testGetVendorById() throws Exception {
		given(this.vendorService.getVendorById(anyLong())).willReturn(this.vendorDTO_1);
		
		this.mockMvc.perform(get(VENDOR_URL_PREFIX + VEND_ID_1)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.name", equalTo(this.vendorDTO_1.getName())));
		then(this.vendorService).should(times(1)).getVendorById(anyLong());
		then(this.vendorService).shouldHaveNoMoreInteractions();
	}
	
	@Test
	public void testGetVendorByIdNotFound() throws Exception {
		given(this.vendorService.getVendorById(anyLong())).willThrow(ResourceNotFoundException.class);
		
		this.mockMvc.perform(get(VENDOR_URL_PREFIX + VEND_ID_1)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isNotFound());
	}
	
	@Test
	public void testCreateNewVendor() throws Exception {
		given(this.vendorService.createNewVendor(any(VendorDTO.class))).willReturn(this.vendorDTO_1);
		
		this.mockMvc.perform(post(VENDOR_URL_PREFIX)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.content(asJsonString(this.vendorDTO_1)))
			.andExpect(status().isCreated())
			.andExpect(jsonPath("$.name", equalTo(this.vendorDTO_1.getName())))
			.andExpect(jsonPath("$.vendor_url", equalTo(this.vendorDTO_1.getVendorUrl())));
		then(this.vendorService).should(times(1)).createNewVendor(any(VendorDTO.class));
		then(this.vendorService).shouldHaveNoMoreInteractions();
	}
	
	@Test
	public void testUpdateVendor() throws Exception {
		given(this.vendorService.updateVendor(anyLong(), any(VendorDTO.class))).willReturn(this.vendorDTO_1);
		
		this.mockMvc.perform(put(VENDOR_URL_PREFIX + VEND_ID_1)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.content(asJsonString(this.vendorDTO_1)))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.name", equalTo(this.vendorDTO_1.getName())))
			.andExpect(jsonPath("$.vendor_url", equalTo(this.vendorDTO_1.getVendorUrl())));
		then(this.vendorService).should(times(1)).updateVendor(anyLong(), any(VendorDTO.class));
		then(this.vendorService).shouldHaveNoMoreInteractions();
	}
	
	@Test
	public void testPatchVendor() throws Exception {
		given(this.vendorService.patchVendor(anyLong(), any(VendorDTO.class))).willReturn(this.vendorDTO_1);
		
		this.mockMvc.perform(patch(VENDOR_URL_PREFIX + VEND_ID_1)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.content(asJsonString(this.vendorDTO_1)))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.name", equalTo(this.vendorDTO_1.getName())))
			.andExpect(jsonPath("$.vendor_url", equalTo(this.vendorDTO_1.getVendorUrl())));
		then(this.vendorService).should(times(1)).patchVendor(anyLong(), any(VendorDTO.class));
		then(this.vendorService).shouldHaveNoMoreInteractions();
	}
	
	@Test
	public void testDeleteVendor() throws Exception {
		this.mockMvc.perform(delete(VENDOR_URL_PREFIX + VEND_ID_1)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk());
		then(this.vendorService).should(times(1)).deleteVendorById(anyLong());
		then(this.vendorService).shouldHaveNoMoreInteractions();
	}
}
