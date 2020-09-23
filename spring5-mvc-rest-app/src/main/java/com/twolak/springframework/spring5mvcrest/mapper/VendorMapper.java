/**
 * 
 */
package com.twolak.springframework.spring5mvcrest.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.twolak.springframework.spring5mvcrest.api.v1.model.VendorDTO;
import com.twolak.springframework.spring5mvcrest.controllers.v1.VendorController;
import com.twolak.springframework.spring5mvcrest.domain.Vendor;



/**
 * @author twolak
 *
 */
@Mapper(componentModel = "spring")
public interface VendorMapper {
	public static final String VENDOR_URL_FORMAT = VendorController.BASE_URL + "/%d";
	VendorMapper INSANCE = Mappers.getMapper(VendorMapper.class);
	
	@Mapping(target = "vendorUrl", expression = "java(String.format(VENDOR_URL_FORMAT, vendor.getId()))")
	VendorDTO vendorToVendorDTO(Vendor vendor);
	
	Vendor vendorDTOToVendor(VendorDTO vendorDTO); 
}
