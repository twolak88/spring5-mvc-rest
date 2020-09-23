package com.twolak.springframework.spring5mvcrest.api.v1.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author twolak
 *
 */
@Getter
@Setter
public class CustomerDTO {
	@ApiModelProperty(value = "This is the first name", required = true)
	private String firstname;
	@ApiModelProperty(value = "This is the last name", required = true)
	private String lastname;
	
	@JsonProperty("customer_url")
	private String customerUrl;
}
