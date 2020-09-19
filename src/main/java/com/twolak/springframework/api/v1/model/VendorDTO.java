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
public class VendorDTO {
	private String name;
	
	@JsonProperty("vendor_url")
	private String vendorUrl;
}
