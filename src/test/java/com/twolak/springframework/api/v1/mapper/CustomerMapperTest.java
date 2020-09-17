/**
 * 
 */
package com.twolak.springframework.api.v1.mapper;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.twolak.springframework.api.v1.model.CustomerDTO;
import com.twolak.springframework.domain.Customer;

/**
 * @author twolak
 *
 */
class CustomerMapperTest {
	private static final String CUSTOMER_URL_PREFIX = "/api/v1/customers/";
	private static final Long ID = 1L;
	private static final String FIRSTNAME = "Tom";
	private static final String LASTNAME = "Lastname";
	private CustomerMapper customerMapper = CustomerMapper.INSANCE;
	
	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
	void testCustomerToCustomerDTO() {
		Customer customer = new Customer();
		customer.setId(ID);
		customer.setFirstname(FIRSTNAME);
		customer.setLastname(LASTNAME);
		
		CustomerDTO customerDTO = this.customerMapper.customerToCustomerDTO(customer);
		
		assertEquals(FIRSTNAME, customerDTO.getFirstname());
		assertEquals(LASTNAME, customerDTO.getLastname());
		assertEquals(CUSTOMER_URL_PREFIX + ID, customerDTO.getCustomerUrl());
	}

}
