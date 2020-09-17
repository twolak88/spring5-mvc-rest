package com.twolak.springframework.api.v1.model;

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
	private String customerUrl;
}
