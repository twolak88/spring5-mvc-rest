/**
 * 
 */
package com.twolak.springframework.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.twolak.springframework.api.v1.model.CustomerDTO;
import com.twolak.springframework.domain.Customer;
import com.twolak.springframework.mapper.CustomerMapper;
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

	@Override
	public CustomerDTO createNewCustomer(CustomerDTO customerDTO) {
		return saveCustomer(this.customerMapper.customerDTOToCustomer(customerDTO));
	}

	@Override
	public CustomerDTO updateCustomer(Long id, CustomerDTO customerDTO) {
		Customer customer = this.customerMapper.customerDTOToCustomer(customerDTO);
		customer.setId(id);
		return saveCustomer(customer);
	}
	
	@Override
	public CustomerDTO patchCustomer(Long id, CustomerDTO customerDTO) {
		return this.customerRepository.findById(id).map(customer -> {
			if (customerDTO.getFirstname() != null) {
				customer.setFirstname(customerDTO.getFirstname());
			}
			if (customerDTO.getLastname() != null) {
				customer.setLastname(customerDTO.getLastname());
			}
			return this.customerMapper.customerToCustomerDTO(this.customerRepository.save(customer));
		}).orElseThrow(() -> new RuntimeException("Customer for id: " + id + " not found"));
	}
	
	@Override
	public void deleteCustomerById(Long id) {
		this.customerRepository.deleteById(id);
	}
	
	private CustomerDTO saveCustomer(Customer customer) {
		Customer savedCustomer = this.customerRepository.save(customer);
		return this.customerMapper.customerToCustomerDTO(savedCustomer);
	}
}
