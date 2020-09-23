package com.twolak.springframework.spring5mvcrest.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.any;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.twolak.springframework.model.CustomerDTO;
import com.twolak.springframework.spring5mvcrest.controllers.v1.CustomerController;
import com.twolak.springframework.spring5mvcrest.domain.Customer;
import com.twolak.springframework.spring5mvcrest.mapper.CustomerMapper;
import com.twolak.springframework.spring5mvcrest.repositories.CustomerRepository;
import com.twolak.springframework.spring5mvcrest.services.impl.CustomerServiceImpl;

/**
 * @author twolak
 *
 */
@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {
	private static final String CUSTOMER_URL_PREFIX = CustomerController.BASE_URL + "/";
	private static final Long ID = 1L;
	private static final String FIRSTNAME = "Tom";
	private static final String PATCHED_FIRSTNAME = "Rob";
	private static final String LASTNAME = "Lastname";
	
	@Mock
	private CustomerRepository customerRepository;
	
	private CustomerService customerService;
	
	@BeforeEach
	void setUp() throws Exception {
		this.customerService = new CustomerServiceImpl(this.customerRepository, CustomerMapper.INSANCE);
	}

	@Test
	void testGetAllCustomers() {
		when(this.customerRepository.findAll()).thenReturn(Arrays.asList(new Customer(), new Customer()));
		
		List<CustomerDTO> customerDTOs = this.customerService.getAllCustomers();
		
		assertEquals(2, customerDTOs.size());
		verify(this.customerRepository, times(1)).findAll();
		verifyNoMoreInteractions(this.customerRepository);
	}

	@Test
	void testGetCustomerById() {
		Customer customer = new Customer();
		customer.setId(ID);
		customer.setFirstname(FIRSTNAME);
		customer.setLastname(LASTNAME);
		
		when(this.customerRepository.findById(anyLong())).thenReturn(Optional.of(customer));
		
		CustomerDTO customerDTO = this.customerService.getCustomerById(ID);
		
		assertNotNull(customerDTO);
		assertEquals(FIRSTNAME, customerDTO.getFirstname());
		assertEquals(LASTNAME, customerDTO.getLastname());
		assertEquals(CUSTOMER_URL_PREFIX + ID, customerDTO.getCustomerUrl());
		verify(this.customerRepository, times(1)).findById(anyLong());
		verifyNoMoreInteractions(this.customerRepository);
	}
	
	@Test
	void testCreateNewCustomer() {
		CustomerDTO customerDTO = new CustomerDTO();
		customerDTO.setFirstname(FIRSTNAME);
		customerDTO.setLastname(LASTNAME);
		
		Customer savedCustomer = new Customer();
		savedCustomer.setFirstname(FIRSTNAME);
		savedCustomer.setLastname(LASTNAME);
		savedCustomer.setId(ID);
		
		when(this.customerRepository.save(any(Customer.class))).thenReturn(savedCustomer);
		
		CustomerDTO savedCustomerDTO = this.customerService.createNewCustomer(customerDTO);
		
		assertEquals(customerDTO.getFirstname(), savedCustomerDTO.getFirstname());
		assertEquals(customerDTO.getLastname(), savedCustomerDTO.getLastname());
		assertEquals(CUSTOMER_URL_PREFIX + ID, savedCustomerDTO.getCustomerUrl());
		verify(this.customerRepository, times(1)).save(any(Customer.class));
		verifyNoMoreInteractions(this.customerRepository);
	}
	
	@Test
	void testUpdateCustomer() {
		CustomerDTO customerDTO = new CustomerDTO();
		customerDTO.setFirstname(FIRSTNAME);
		customerDTO.setLastname(LASTNAME);
		
		Customer savedCustomer = new Customer();
		savedCustomer.setFirstname(FIRSTNAME);
		savedCustomer.setLastname(LASTNAME);
		savedCustomer.setId(ID);
		
		when(this.customerRepository.save(any(Customer.class))).thenReturn(savedCustomer);
		
		CustomerDTO savedCustomerDTO = this.customerService.updateCustomer(ID, customerDTO);
		
		assertEquals(customerDTO.getFirstname(), savedCustomerDTO.getFirstname());
		assertEquals(customerDTO.getLastname(), savedCustomerDTO.getLastname());
		assertEquals(CUSTOMER_URL_PREFIX + ID, savedCustomerDTO.getCustomerUrl());
		verify(this.customerRepository, times(1)).save(any(Customer.class));
		verifyNoMoreInteractions(this.customerRepository);
	}
	
	@Test
	void testPatchCustomer() {
		CustomerDTO customerDTO = new CustomerDTO();
		customerDTO.setFirstname(PATCHED_FIRSTNAME);
		
		Customer foundCustomer = new Customer();
		foundCustomer.setFirstname(FIRSTNAME);
		foundCustomer.setLastname(LASTNAME);
		foundCustomer.setId(ID);
		
		Customer savedCustomer = new Customer();
		savedCustomer.setFirstname(PATCHED_FIRSTNAME);
		savedCustomer.setLastname(LASTNAME);
		savedCustomer.setId(ID);
		
		when(this.customerRepository.findById(anyLong())).thenReturn(Optional.of(foundCustomer));
		when(this.customerRepository.save(any(Customer.class))).thenReturn(savedCustomer);
		
		CustomerDTO savedCustomerDTO = this.customerService.patchCustomer(ID, customerDTO);
		
		assertEquals(savedCustomer.getFirstname(), savedCustomerDTO.getFirstname());
		assertEquals(savedCustomer.getLastname(), savedCustomerDTO.getLastname());
		assertEquals(CUSTOMER_URL_PREFIX + ID, savedCustomerDTO.getCustomerUrl());
		verify(this.customerRepository, times(1)).save(any(Customer.class));
		verify(this.customerRepository, times(1)).findById(anyLong());
		verifyNoMoreInteractions(this.customerRepository);
	}
	
	@Test
	void testDeleteCustomerById() {
		this.customerRepository.deleteById(ID);
		
		verify(this.customerRepository, times(1)).deleteById(anyLong());
		verifyNoMoreInteractions(this.customerRepository);
	}
}
