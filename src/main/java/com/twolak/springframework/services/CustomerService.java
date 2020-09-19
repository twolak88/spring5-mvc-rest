/**
 * 
 */
package com.twolak.springframework.services;

import java.util.List;

import com.twolak.springframework.api.v1.model.CustomerDTO;

/**
 * @author twolak
 *
 */
public interface CustomerService {
	List<CustomerDTO> getAllCustomers();
	CustomerDTO getCustomerById(Long id);
	CustomerDTO createNewCustomer(CustomerDTO customerDTO);
	CustomerDTO updateCustomer(Long id, CustomerDTO customerDTO);
	CustomerDTO patchCustomer(Long id, CustomerDTO customerDTO);
	void deleteCustomerById(Long id);
}
