/**
 * 
 */
package com.twolak.springframework.spring5mvcrest.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.twolak.springframework.model.CustomerDTO;
import com.twolak.springframework.spring5mvcrest.controllers.v1.CustomerController;
import com.twolak.springframework.spring5mvcrest.domain.Customer;

/**
 * @author twolak
 *
 */
@Mapper(componentModel = "spring")
public interface CustomerMapper {
	public static final String CUSTOMER_URL_FORMAT = CustomerController.BASE_URL + "/%d";
	CustomerMapper INSANCE = Mappers.getMapper(CustomerMapper.class);
	
	@Mapping(target = "customerUrl", expression = "java(String.format(CUSTOMER_URL_FORMAT, customer.getId()))")
	CustomerDTO customerToCustomerDTO(Customer customer);
	
	Customer customerDTOToCustomer(CustomerDTO customerDTO);
}
