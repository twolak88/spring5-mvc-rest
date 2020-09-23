/**
 * 
 */
package com.twolak.springframework.spring5mvcrest.services;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.twolak.springframework.model.CustomerDTO;
import com.twolak.springframework.spring5mvcrest.bootstrap.Bootstrap;
import com.twolak.springframework.spring5mvcrest.domain.Customer;
import com.twolak.springframework.spring5mvcrest.mapper.CustomerMapper;
import com.twolak.springframework.spring5mvcrest.repositories.CategoryRepository;
import com.twolak.springframework.spring5mvcrest.repositories.CustomerRepository;
import com.twolak.springframework.spring5mvcrest.repositories.VendorRepository;
import com.twolak.springframework.spring5mvcrest.services.impl.CustomerServiceImpl;

/**
 * @author twolak
 *
 */
@ExtendWith(SpringExtension.class)
@DataJpaTest
class CustomerServiceIT {
	
	private static final String UPDATED_NAME = "UpdatedName";

	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	private VendorRepository vendorRepository;
	
	private CustomerService customerService;
	
	@BeforeEach
	void setUp() throws Exception {
		Bootstrap bootstrap = new Bootstrap(this.categoryRepository, this.customerRepository, this.vendorRepository);
		bootstrap.run();
		
		this.customerService = new CustomerServiceImpl(this.customerRepository, CustomerMapper.INSANCE);
	}
	
//	@AfterEach
//	void clean() {
//		this.categoryRepository.deleteAll();
//		this.customerRepository.deleteAll();
//	}
	
	@Test
	void testPatchCustomerFirstname() {
		String updatedName = UPDATED_NAME;
		Long id = getCustomerIdValue();
		
		Customer originalCustomer = this.customerRepository.getOne(id);
		assertNotNull(originalCustomer);
		
		String originalFirstname = originalCustomer.getFirstname();
		String originalLastname = originalCustomer.getLastname();
		
		CustomerDTO customerDTO = new CustomerDTO();
		customerDTO.setFirstname(updatedName);
		
		this.customerService.patchCustomer(id, customerDTO);
		Customer updatedCustomer = this.customerRepository.findById(id).get();
		
		assertNotNull(updatedCustomer);
		assertEquals(updatedName, updatedCustomer.getFirstname());
		assertThat(originalFirstname, not(equalTo(updatedCustomer.getFirstname())));
		assertThat(originalLastname, equalTo(updatedCustomer.getLastname()));
	}
	
	@Test
	void testPatchCustomerLastname() {
		String updatedName = UPDATED_NAME;
		Long id = getCustomerIdValue();
		
		Customer originalCustomer = this.customerRepository.getOne(id);
		assertNotNull(originalCustomer);
		
		String originalFirstname = originalCustomer.getFirstname();
		String originalLastname = originalCustomer.getLastname();
		
		CustomerDTO customerDTO = new CustomerDTO();
		customerDTO.setLastname(updatedName);
		
		this.customerService.patchCustomer(id, customerDTO);
		Customer updatedCustomer = this.customerRepository.findById(id).get();
		
		assertNotNull(updatedCustomer);
		assertEquals(updatedName, updatedCustomer.getLastname());
		assertThat(originalFirstname, equalTo(updatedCustomer.getFirstname()));
		assertThat(originalLastname, not(equalTo(updatedCustomer.getLastname())));
	}
	
	private Long getCustomerIdValue() {
		List<Customer> customers = this.customerRepository.findAll();
		return customers.get(0).getId();
	}

}
