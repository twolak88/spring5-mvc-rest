package com.twolak.springframework.api.v1.model;

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
public class VendorDTO {
	@ApiModelProperty(value = "This is the company name", required = true)
	private String name;
	
	@ApiModelProperty(value = "This is the url")
	@JsonProperty("vendor_url")
	private String vendorUrl;
}
