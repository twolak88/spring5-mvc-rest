/**
 * 
 */
package com.twolak.springframework.controllers.v1;

import static com.twolak.springframework.controllers.v1.AbstractRestControllerTest.asJsonString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.any;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.twolak.springframework.api.v1.model.VendorDTO;
import com.twolak.springframework.api.v1.model.VendorListDTO;
import com.twolak.springframework.services.ResourceNotFoundException;
import com.twolak.springframework.services.VendorService;

/**
 * @author twolak
 *
 */
@ExtendWith(MockitoExtension.class)
public class VendorControllerTest {
	private static final Long VEND_ID_1 = 1L;
	private static final Long VEND_ID_2 = 2L;
	private static final String NAME = "Corp LTD";
	private static final String PATCHED_NAME = "New Corp";
	private static final String VENDOR_URL_PREFIX = VendorController.BASE_URL + "/";
	
	@Mock
	private VendorService vendorService;
	
	@InjectMocks
	private VendorController vendorController;
	
	private MockMvc mockMvc;
	
	@BeforeEach
	void setUp() throws Exception {
		this.mockMvc = MockMvcBuilders.standaloneSetup(this.vendorController)
				.setControllerAdvice(new RestResponseEntityExceptionHandler()).build();
	}
	
	@Test
	public void testListAllVendors() throws Exception {
		VendorDTO vendorDTO = new VendorDTO();
		vendorDTO.setName(NAME);
		vendorDTO.setVendorUrl(VENDOR_URL_PREFIX + VEND_ID_1);
		VendorDTO vendorDTO2 = new VendorDTO();
		vendorDTO2.setName(NAME);
		vendorDTO2.setVendorUrl(VENDOR_URL_PREFIX + VEND_ID_2);
		
		when(this.vendorService.getAllVendors()).thenReturn(new VendorListDTO(Arrays.asList(vendorDTO, vendorDTO2)));
		
		this.mockMvc.perform(get(VENDOR_URL_PREFIX)
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.vendors", hasSize(2)));
		verify(this.vendorService, times(1)).getAllVendors();
		verifyNoMoreInteractions(this.vendorService);
	}
	
	@Test
	public void testGetVendorById() throws Exception {
		VendorDTO vendorDTO = new VendorDTO();
		vendorDTO.setName(NAME);
		vendorDTO.setVendorUrl(VENDOR_URL_PREFIX + VEND_ID_1);
		
		when(this.vendorService.getVendorById(anyLong())).thenReturn(vendorDTO);
		
		this.mockMvc.perform(get(VENDOR_URL_PREFIX + VEND_ID_1)
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.name", equalTo(NAME)));
		verify(this.vendorService, times(1)).getVendorById(anyLong());
		verifyNoMoreInteractions(this.vendorService);
	}
	
	@Test
	public void testGetVendorByIdNotFound() throws Exception {
		when(this.vendorService.getVendorById(anyLong())).thenThrow(ResourceNotFoundException.class);
		
		this.mockMvc.perform(get(VENDOR_URL_PREFIX + VEND_ID_1)
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isNotFound());
	}
	
	@Test
	public void testCreateNewVendor() throws Exception {
		VendorDTO vendorDTO = new VendorDTO();
		vendorDTO.setName(NAME);
		
		VendorDTO savedVendorDTO = new VendorDTO();
		savedVendorDTO.setName(NAME);
		savedVendorDTO.setVendorUrl(VENDOR_URL_PREFIX + VEND_ID_1);
		
		when(this.vendorService.createNewVendor(any(VendorDTO.class))).thenReturn(savedVendorDTO);
		
		this.mockMvc.perform(post(VENDOR_URL_PREFIX)
				.contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(vendorDTO)))
			.andExpect(status().isCreated())
			.andExpect(jsonPath("$.name", equalTo(NAME)))
			.andExpect(jsonPath("$.vendor_url", equalTo(VENDOR_URL_PREFIX + VEND_ID_1)));
		verify(this.vendorService, times(1)).createNewVendor(any(VendorDTO.class));
		verifyNoMoreInteractions(this.vendorService);
	}
	
	@Test
	public void testUpdateVendor() throws Exception {
		VendorDTO vendorDTO = new VendorDTO();
		vendorDTO.setName(NAME);
		
		VendorDTO savedVendorDTO = new VendorDTO();
		savedVendorDTO.setName(NAME);
		savedVendorDTO.setVendorUrl(VENDOR_URL_PREFIX + VEND_ID_1);
		
		when(this.vendorService.updateVendor(anyLong(), any(VendorDTO.class))).thenReturn(savedVendorDTO);
		
		this.mockMvc.perform(put(VENDOR_URL_PREFIX + VEND_ID_1)
				.contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(vendorDTO)))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.name", equalTo(NAME)))
			.andExpect(jsonPath("$.vendor_url", equalTo(VENDOR_URL_PREFIX + VEND_ID_1)));
		verify(this.vendorService, times(1)).updateVendor(anyLong(), any(VendorDTO.class));
		verifyNoMoreInteractions(this.vendorService);
	}
	
	@Test
	public void testPatchVendor() throws Exception {
		VendorDTO vendorDTO = new VendorDTO();
		vendorDTO.setName(PATCHED_NAME);
		
		VendorDTO savedVendorDTO = new VendorDTO();
		savedVendorDTO.setName(vendorDTO.getName());
		savedVendorDTO.setVendorUrl(VENDOR_URL_PREFIX + VEND_ID_1);
		
		when(this.vendorService.patchVendor(anyLong(), any(VendorDTO.class))).thenReturn(savedVendorDTO);
		
		this.mockMvc.perform(patch(VENDOR_URL_PREFIX + VEND_ID_1)
				.contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(vendorDTO)))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.name", equalTo(PATCHED_NAME)))
			.andExpect(jsonPath("$.vendor_url", equalTo(VENDOR_URL_PREFIX + VEND_ID_1)));
		verify(this.vendorService, times(1)).patchVendor(anyLong(), any(VendorDTO.class));
		verifyNoMoreInteractions(this.vendorService);
	}
	
	@Test
	public void testDeleteVendor() throws Exception {
		this.mockMvc.perform(delete(VENDOR_URL_PREFIX + VEND_ID_1)
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk());
		verify(this.vendorService, times(1)).deleteVendorById(anyLong());
		verifyNoMoreInteractions(this.vendorService);
	}
}
