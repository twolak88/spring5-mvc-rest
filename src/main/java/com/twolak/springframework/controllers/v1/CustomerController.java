package com.twolak.springframework.controllers.v1;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
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
@RequestMapping(CustomerController.BASE_URL)
public class CustomerController {
	
	public static final String BASE_URL = "/api/v1/customers";
	public static final String ID_PROPERTY = "id";
	public static final String ID_URL_PROPERTY = "{" + ID_PROPERTY + "}";
	
	private final CustomerService customerService;

	public CustomerController(CustomerService customerService) {
		this.customerService = customerService;
	}
	
	@GetMapping
	public ResponseEntity<CustomerListDTO> getAllCustomers() {
		return new ResponseEntity<CustomerListDTO>(new CustomerListDTO(this.customerService.getAllCustomers()), HttpStatus.OK);
	}
	
	@GetMapping(ID_URL_PROPERTY)
	public ResponseEntity<CustomerDTO> getCustomerById(@PathVariable(ID_PROPERTY) Long id) {
		return new ResponseEntity<CustomerDTO>(this.customerService.getCustomerById(id), HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<CustomerDTO> createNewCustomer(@RequestBody CustomerDTO customerDTO) {
		return new ResponseEntity<CustomerDTO>(this.customerService.createNewCustomer(customerDTO), HttpStatus.CREATED);
	}
	
	@PutMapping(ID_URL_PROPERTY)
	public ResponseEntity<CustomerDTO> updateCustomer(@PathVariable(ID_PROPERTY) Long id, @RequestBody CustomerDTO customerDTO) {
		return new ResponseEntity<CustomerDTO>(this.customerService.updateCustomer(id, customerDTO), HttpStatus.OK);
	}
	
	@PatchMapping(ID_URL_PROPERTY)
	public ResponseEntity<CustomerDTO> patchCustomer(@PathVariable(ID_PROPERTY) Long id, @RequestBody CustomerDTO customerDTO) {
		return new ResponseEntity<CustomerDTO>(this.customerService.patchCustomer(id, customerDTO), HttpStatus.OK);
	}
	
	@DeleteMapping(ID_URL_PROPERTY)
	public ResponseEntity<Void> deleteCustomer(@PathVariable(ID_PROPERTY) Long id) {
		this.customerService.deleteCustomerById(id);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
}
