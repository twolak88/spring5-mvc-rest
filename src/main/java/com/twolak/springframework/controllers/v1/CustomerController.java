package com.twolak.springframework.controllers.v1;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.twolak.springframework.api.v1.model.CustomerDTO;
import com.twolak.springframework.api.v1.model.CustomerListDTO;
import com.twolak.springframework.services.CustomerService;

/**
 * @author twolak
 *
 */
@Controller
@RequestMapping("/api/v1/customers")
public class CustomerController {
	
	private final CustomerService customerService;

	public CustomerController(CustomerService customerService) {
		this.customerService = customerService;
	}
	
	@GetMapping
	public ResponseEntity<CustomerListDTO> getAllCustomers() {
		return new ResponseEntity<CustomerListDTO>(new CustomerListDTO(this.customerService.getAllCustomers()), HttpStatus.OK);
	}
	
	@GetMapping("{id}")
	public ResponseEntity<CustomerDTO> getCustomerById(@PathVariable("id") Long id) {
		return new ResponseEntity<CustomerDTO>(this.customerService.getCustomerById(id), HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<CustomerDTO> createNewCustomer(@RequestBody CustomerDTO customerDTO) {
		return new ResponseEntity<CustomerDTO>(this.customerService.createNewCustomer(customerDTO), HttpStatus.CREATED);
	}
	
	@PutMapping("{id}")
	public ResponseEntity<CustomerDTO> updateCustomer(@PathVariable("id") Long id, @RequestBody CustomerDTO customerDTO) {
		return new ResponseEntity<CustomerDTO>(this.customerService.updateCustomer(id, customerDTO), HttpStatus.OK);
	}
	
	@PatchMapping("{id}")
	public ResponseEntity<CustomerDTO> patchCustomer(@PathVariable("id") Long id, @RequestBody CustomerDTO customerDTO) {
		return new ResponseEntity<CustomerDTO>(this.customerService.patchCustomer(id, customerDTO), HttpStatus.OK);
	}
}
