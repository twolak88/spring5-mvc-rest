package com.twolak.springframework.spring5mvcrest.controllers.v1;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.twolak.springframework.model.CustomerDTO;
import com.twolak.springframework.model.CustomerListDTO;
import com.twolak.springframework.spring5mvcrest.services.CustomerService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author twolak
 *
 */
@Api(description = "This is my Customer Controller")
@RestController
@RequestMapping(CustomerController.BASE_URL)
public class CustomerController {
	
	public static final String BASE_URL = "/api/v1/customers";
	public static final String ID_PROPERTY = "id";
	public static final String ID_URL_PROPERTY = "{" + ID_PROPERTY + "}";
	
	private final CustomerService customerService;

	public CustomerController(CustomerService customerService) {
		this.customerService = customerService;
	}
	
	@ApiOperation(value = "This will get a list of customers", notes = "There are some notes about the API")
	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public CustomerListDTO getAllCustomers() {
		CustomerListDTO customerListDTO = new CustomerListDTO();
		customerListDTO.getCustomers().addAll(this.customerService.getAllCustomers());
		return customerListDTO;
	}
	
	@GetMapping(ID_URL_PROPERTY)
	@ResponseStatus(HttpStatus.OK)
	public CustomerDTO getCustomerById(@PathVariable(ID_PROPERTY) Long id) {
		return this.customerService.getCustomerById(id);
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public CustomerDTO createNewCustomer(@RequestBody CustomerDTO customerDTO) {
		return this.customerService.createNewCustomer(customerDTO);
	}
	
	@PutMapping(ID_URL_PROPERTY)
	@ResponseStatus(HttpStatus.OK)
	public CustomerDTO updateCustomer(@PathVariable(ID_PROPERTY) Long id, @RequestBody CustomerDTO customerDTO) {
		return this.customerService.updateCustomer(id, customerDTO);
	}
	
	@PatchMapping(ID_URL_PROPERTY)
	@ResponseStatus(HttpStatus.OK)
	public CustomerDTO patchCustomer(@PathVariable(ID_PROPERTY) Long id, @RequestBody CustomerDTO customerDTO) {
		return this.customerService.patchCustomer(id, customerDTO);
	}
	
	@DeleteMapping(ID_URL_PROPERTY)
	@ResponseStatus(HttpStatus.OK)
	public void deleteCustomer(@PathVariable(ID_PROPERTY) Long id) {
		this.customerService.deleteCustomerById(id);
	}
}
