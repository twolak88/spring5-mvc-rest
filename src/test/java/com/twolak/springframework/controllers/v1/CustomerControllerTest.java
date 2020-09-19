/**
 * 
 */
package com.twolak.springframework.controllers.v1;

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
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
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

import com.twolak.springframework.api.v1.model.CustomerDTO;
import com.twolak.springframework.services.CustomerService;
import com.twolak.springframework.services.ResourceNotFoundException;

import static com.twolak.springframework.controllers.v1.AbstractRestControllerTest.asJsonString;

/**
 * @author twolak
 *
 */
@ExtendWith(MockitoExtension.class)
class CustomerControllerTest {
	private static final Long CUST_ID_1 = 1L;
	private static final Long CUST_ID_2 = 2L;
	private static final String FIRSTNAME = "Tom";
	private static final String PATCHED_FIRSTNAME = "Rob";
	private static final String LASTNAME = "Lastname";
	private static final String CUSTOMER_URL_PREFIX = CustomerController.BASE_URL + "/";

	@Mock
	private CustomerService customerService;
	
	@InjectMocks
	private CustomerController customerController;
	
	private MockMvc mockMvc;
	
	@BeforeEach
	void setUp() throws Exception {
		this.mockMvc = MockMvcBuilders.standaloneSetup(this.customerController)
				.setControllerAdvice(new RestResponseEntityExceptionHandler()).build();
	}

	@Test
	public void testListAllCustomers() throws Exception {
		CustomerDTO customerDTO = new CustomerDTO();
		customerDTO.setFirstname(FIRSTNAME);
		customerDTO.setLastname(LASTNAME);
		customerDTO.setCustomerUrl(CUSTOMER_URL_PREFIX + CUST_ID_1);
		CustomerDTO customerDTO2 = new CustomerDTO();
		customerDTO2.setFirstname(FIRSTNAME + 2);
		customerDTO2.setLastname(LASTNAME + 2);
		customerDTO2.setCustomerUrl(CUSTOMER_URL_PREFIX + CUST_ID_2);
		
		when(this.customerService.getAllCustomers()).thenReturn(Arrays.asList(customerDTO, customerDTO2));
		
		this.mockMvc.perform(get(CUSTOMER_URL_PREFIX)
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.customers", hasSize(2)));
		verify(this.customerService, times(1)).getAllCustomers();
		verifyNoMoreInteractions(this.customerService);
	}
	
	@Test
	public void testGetCustomerById() throws Exception {
		CustomerDTO customerDTO = new CustomerDTO();
		customerDTO.setFirstname(FIRSTNAME);
		customerDTO.setLastname(LASTNAME);
		customerDTO.setCustomerUrl(CUSTOMER_URL_PREFIX + CUST_ID_1);
		
		when(this.customerService.getCustomerById(anyLong())).thenReturn(customerDTO);
		
		this.mockMvc.perform(get(CUSTOMER_URL_PREFIX + CUST_ID_1)
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.lastname", equalTo(LASTNAME)));
		verify(this.customerService, times(1)).getCustomerById(anyLong());
		verifyNoMoreInteractions(this.customerService);
	}
	
	@Test
	public void testGetByIdNotFound() throws Exception {
		when(this.customerService.getCustomerById(anyLong())).thenThrow(ResourceNotFoundException.class);
		
		this.mockMvc.perform(get(CUSTOMER_URL_PREFIX + CUST_ID_1)
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isNotFound());
	}
	
	@Test
	public void testCreateNewCustomer() throws Exception {
		CustomerDTO customerDTO = new CustomerDTO();
		customerDTO.setFirstname(FIRSTNAME);
		customerDTO.setLastname(LASTNAME);
		
		CustomerDTO savedCustomerDTO = new CustomerDTO();
		savedCustomerDTO.setFirstname(FIRSTNAME);
		savedCustomerDTO.setLastname(LASTNAME);
		savedCustomerDTO.setCustomerUrl(CUSTOMER_URL_PREFIX + CUST_ID_1);
		
		when(this.customerService.createNewCustomer(any(CustomerDTO.class))).thenReturn(savedCustomerDTO);
		
		this.mockMvc.perform(post(CUSTOMER_URL_PREFIX)
				.contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(customerDTO)))
			.andExpect(status().isCreated())
			.andExpect(jsonPath("$.firstname", equalTo(FIRSTNAME)))
			.andExpect(jsonPath("$.lastname", equalTo(LASTNAME)))
			.andExpect(jsonPath("$.customer_url", equalTo(CUSTOMER_URL_PREFIX + CUST_ID_1)));
		verify(this.customerService, times(1)).createNewCustomer(any(CustomerDTO.class));
		verifyNoMoreInteractions(this.customerService);
	}
	
	@Test
	public void testUpdateCustomer() throws Exception {
		CustomerDTO customerDTO = new CustomerDTO();
		customerDTO.setFirstname(FIRSTNAME);
		customerDTO.setLastname(LASTNAME);
		
		CustomerDTO savedCustomerDTO = new CustomerDTO();
		savedCustomerDTO.setFirstname(FIRSTNAME);
		savedCustomerDTO.setLastname(LASTNAME);
		savedCustomerDTO.setCustomerUrl(CUSTOMER_URL_PREFIX + CUST_ID_1);
		
		when(this.customerService.updateCustomer(anyLong(), any(CustomerDTO.class))).thenReturn(savedCustomerDTO);
		
		this.mockMvc.perform(put(CUSTOMER_URL_PREFIX + CUST_ID_1)
				.contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(customerDTO)))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.firstname", equalTo(FIRSTNAME)))
			.andExpect(jsonPath("$.lastname", equalTo(LASTNAME)))
			.andExpect(jsonPath("$.customer_url", equalTo(CUSTOMER_URL_PREFIX + CUST_ID_1)));
		verify(this.customerService, times(1)).updateCustomer(anyLong(), any(CustomerDTO.class));
		verifyNoMoreInteractions(this.customerService);
	}
	
	@Test
	public void testPatchCustomer() throws Exception {
		CustomerDTO customerDTO = new CustomerDTO();
		customerDTO.setFirstname(PATCHED_FIRSTNAME);
		
		CustomerDTO returnCustomerDTO = new CustomerDTO();
		returnCustomerDTO.setFirstname(customerDTO.getFirstname());
		returnCustomerDTO.setLastname(LASTNAME);
		returnCustomerDTO.setCustomerUrl(CUSTOMER_URL_PREFIX + CUST_ID_1);
		
		when(this.customerService.patchCustomer(anyLong(), any(CustomerDTO.class))).thenReturn(returnCustomerDTO);
		
		this.mockMvc.perform(patch(CUSTOMER_URL_PREFIX + CUST_ID_1)
				.contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(customerDTO)))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.firstname", equalTo(PATCHED_FIRSTNAME)))
			.andExpect(jsonPath("$.lastname", equalTo(LASTNAME)))
			.andExpect(jsonPath("$.customer_url", equalTo(CUSTOMER_URL_PREFIX + CUST_ID_1)));
		verify(this.customerService, times(1)).patchCustomer(anyLong(), any(CustomerDTO.class));
		verifyNoMoreInteractions(this.customerService);
	}
	
	@Test
	public void testDeleteCustomer() throws Exception {
		this.mockMvc.perform(delete(CUSTOMER_URL_PREFIX + CUST_ID_1)
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk());
		verify(this.customerService, times(1)).deleteCustomerById(anyLong());
		verifyNoMoreInteractions(this.customerService);
	}
}
