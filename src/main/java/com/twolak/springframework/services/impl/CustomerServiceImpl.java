/**
 * 
 */
package com.twolak.springframework.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.twolak.springframework.api.v1.mapper.CustomerMapper;
import com.twolak.springframework.api.v1.model.CustomerDTO;
import com.twolak.springframework.repositories.CustomerRepository;
import com.twolak.springframework.services.CustomerService;

/**
 * @author twolak
 *
 */
@Service
public class CustomerServiceImpl implements CustomerService {
	
	private final CustomerRepository customerRepository;
	private final CustomerMapper customerMapper;
	

	public CustomerServiceImpl(CustomerRepository customerRepository, CustomerMapper customerMapper) {
		this.customerRepository = customerRepository;
		this.customerMapper = customerMapper;
	}

	@Override
	public List<CustomerDTO> getAllCustomers() {
		return this.customerRepository.findAll().stream().map(this.customerMapper::customerToCustomerDTO).collect(Collectors.toList());
	}

	@Override
	public CustomerDTO getCustomerById(Long id) {
		return this.customerRepository.findById(id).map(this.customerMapper::customerToCustomerDTO)
				.orElseThrow(()->new RuntimeException("Customer doesn't exist for id: " + id));
	}

}