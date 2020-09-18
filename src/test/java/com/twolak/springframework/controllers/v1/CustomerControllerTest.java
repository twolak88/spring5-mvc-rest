/**
 * 
 */
package com.twolak.springframework.controllers.v1;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
import static com.twolak.springframework.controllers.v1.AbstractRestControllerTest.asJsonString;

/**
 * @author twolak
 *
 */
@ExtendWith(MockitoExtension.class)
class CustomerControllerTest {
	private static final String FIRSTNAME = "Tom";
	private static final String LASTNAME = "Lastname";
	private static final String CUSTOMER_URL_PREFIX = "/api/v1/customers/";

	@Mock
	private CustomerService customerService;
	
	@InjectMocks
	private CustomerController customerController;
	
	private MockMvc mockMvc;
	
	@BeforeEach
	void setUp() throws Exception {
		this.mockMvc = MockMvcBuilders.standaloneSetup(this.customerController).build();
	}

	@Test
	public void testListAllCustomers() throws Exception {
		
		CustomerDTO customerDTO = new CustomerDTO();
		customerDTO.setFirstname(FIRSTNAME);
		customerDTO.setLastname(LASTNAME);
		customerDTO.setCustomerUrl(CUSTOMER_URL_PREFIX + 1L);
		CustomerDTO customerDTO2 = new CustomerDTO();
		customerDTO2.setFirstname(FIRSTNAME + 2);
		customerDTO2.setLastname(LASTNAME + 2);
		customerDTO2.setCustomerUrl(CUSTOMER_URL_PREFIX + 2L);
		
		when(this.customerService.getAllCustomers()).thenReturn(Arrays.asList(customerDTO, customerDTO2));
		
		this.mockMvc.perform(get("/api/v1/customers")
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
		customerDTO.setCustomerUrl(CUSTOMER_URL_PREFIX + 1L);
		
		when(this.customerService.getCustomerById(anyLong())).thenReturn(customerDTO);
		
		this.mockMvc.perform(get("/api/v1/customers/1")
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.lastname", equalTo(LASTNAME)));
		verify(this.customerService, times(1)).getCustomerById(anyLong());
		verifyNoMoreInteractions(this.customerService);
	}
	
	@Test
	public void testCreateNewCustomer() throws Exception {
		CustomerDTO customerDTO = new CustomerDTO();
		customerDTO.setFirstname(FIRSTNAME);
		customerDTO.setLastname(LASTNAME);
		
		CustomerDTO savedCustomerDTO = new CustomerDTO();
		savedCustomerDTO.setFirstname(FIRSTNAME);
		savedCustomerDTO.setLastname(LASTNAME);
		savedCustomerDTO.setCustomerUrl(CUSTOMER_URL_PREFIX + 1L);
		
		when(this.customerService.createNewCustomer(any(CustomerDTO.class))).thenReturn(savedCustomerDTO);
		
		this.mockMvc.perform(post("/api/v1/customers/")
				.contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(customerDTO)))
			.andExpect(status().isCreated())
			.andExpect(jsonPath("$.firstname", equalTo(FIRSTNAME)))
			.andExpect(jsonPath("$.lastname", equalTo(LASTNAME)))
			.andExpect(jsonPath("$.customer_url", equalTo(CUSTOMER_URL_PREFIX + 1L)));
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
		savedCustomerDTO.setCustomerUrl(CUSTOMER_URL_PREFIX + 1L);
		
		when(this.customerService.updateCustomer(anyLong(), any(CustomerDTO.class))).thenReturn(savedCustomerDTO);
		
		this.mockMvc.perform(put("/api/v1/customers/1")
				.contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(customerDTO)))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.firstname", equalTo(FIRSTNAME)))
			.andExpect(jsonPath("$.lastname", equalTo(LASTNAME)))
			.andExpect(jsonPath("$.customer_url", equalTo(CUSTOMER_URL_PREFIX + 1L)));
		verify(this.customerService, times(1)).updateCustomer(anyLong(), any(CustomerDTO.class));
		verifyNoMoreInteractions(this.customerService);
	}
}
