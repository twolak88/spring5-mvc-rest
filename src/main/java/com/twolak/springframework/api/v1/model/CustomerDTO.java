package com.twolak.springframework.api.v1.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

/**
 * @author twolak
 *
 */
@Getter
@Setter
public class CustomerDTO {
	private String firstname;
	private String lastname;
	
	@JsonProperty("customer_url")
	private String customerUrl;
}
