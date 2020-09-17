package com.twolak.springframework.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.anyLong;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.twolak.springframework.api.v1.mapper.CustomerMapper;
import com.twolak.springframework.api.v1.model.CustomerDTO;
import com.twolak.springframework.domain.Customer;
import com.twolak.springframework.repositories.CustomerRepository;
import com.twolak.springframework.services.impl.CustomerServiceImpl;

/**
 * @author twolak
 *
 */
@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {
	private static final String CUSTOMER_URL_PREFIX = "/api/v1/customers/";
	private static final Long ID = 1L;
	private static final String FIRSTNAME = "Tom";
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

}
