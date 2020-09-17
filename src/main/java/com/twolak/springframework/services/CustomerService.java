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
}
