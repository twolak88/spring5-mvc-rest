package com.twolak.springframework.services;

import com.twolak.springframework.api.v1.model.VendorDTO;
import com.twolak.springframework.api.v1.model.VendorListDTO;

/**
 * @author twolak
 *
 */
public interface VendorService {
	VendorListDTO getAllVendors();
	VendorDTO getVendorById(Long id);
	VendorDTO createNewVendor(VendorDTO vendorDTO);
	VendorDTO updateVendor(Long id, VendorDTO vendorDTO);
	VendorDTO patchVendor(Long id, VendorDTO vendorDTO);
	void deleteVendorById(Long id);
}
