/**
 * 
 */
package com.twolak.springframework.api.v1.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.twolak.springframework.api.v1.model.CustomerDTO;
import com.twolak.springframework.domain.Customer;

/**
 * @author twolak
 *
 */
@Mapper(componentModel = "spring")
public interface CustomerMapper {
	public static final String CUSTOMER_URL_FORMAT = "/api/v1/customers/%d";
	CustomerMapper INSANCE = Mappers.getMapper(CustomerMapper.class);
	
	@Mapping(target = "customerUrl", expression = "java(String.format(CUSTOMER_URL_FORMAT, customer.getId()))")
	CustomerDTO customerToCustomerDTO(Customer customer);
}
