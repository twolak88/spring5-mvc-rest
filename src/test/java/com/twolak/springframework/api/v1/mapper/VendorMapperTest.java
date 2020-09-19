/**
 * 
 */
package com.twolak.springframework.api.v1.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.twolak.springframework.api.v1.model.VendorDTO;
import com.twolak.springframework.controllers.v1.VendorController;
import com.twolak.springframework.domain.Vendor;
import com.twolak.springframework.mapper.VendorMapper;

/**
 * @author twolak
 *
 */
public class VendorMapperTest {
	private static final String VENDOR_URL_PREFIX = VendorController.BASE_URL + "/";
	private static final Long ID = 1L;
	private static final String NAME = "Corp LTD";
	private VendorMapper vendorMapper = VendorMapper.INSANCE;
	
	@BeforeEach
	void setUp() throws Exception {
	}
	
	@Test
	void testVendorToVendorDTO() {
		Vendor vendor = new Vendor();
		vendor.setId(ID);
		vendor.setName(NAME);
		
		VendorDTO vendorDTO = this.vendorMapper.vendorToVendorDTO(vendor);
		
		assertEquals(NAME, vendorDTO.getName());
		assertEquals(VENDOR_URL_PREFIX + ID, vendorDTO.getVendorUrl());
	}
	
	@Test
	void testVendorDTOToVendor() {
		VendorDTO vendorDTO = new VendorDTO();
		vendorDTO.setName(NAME);
		vendorDTO.setVendorUrl(VENDOR_URL_PREFIX + ID);
		
		Vendor vendor = this.vendorMapper.vendorDTOToVendor(vendorDTO);
		
		assertEquals(NAME, vendor.getName());
	}
}
