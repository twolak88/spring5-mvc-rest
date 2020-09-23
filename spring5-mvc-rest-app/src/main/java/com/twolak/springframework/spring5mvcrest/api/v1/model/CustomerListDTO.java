/**
 * 
 */
package com.twolak.springframework.spring5mvcrest.api.v1.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author twolak
 *
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerListDTO {
	List<CustomerDTO> customers;
}
