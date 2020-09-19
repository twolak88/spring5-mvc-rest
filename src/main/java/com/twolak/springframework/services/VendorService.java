package com.twolak.springframework.services;

import java.util.List;

import com.twolak.springframework.api.v1.model.VendorDTO;

/**
 * @author twolak
 *
 */
public interface VendorService {
	List<VendorDTO> getAllVendors();
	VendorDTO getVendorById(Long id);
	VendorDTO createNewVendor(VendorDTO vendorDTO);
	VendorDTO updateVendor(Long id, VendorDTO vendorDTO);
	VendorDTO patchVendor(Long id, VendorDTO vendorDTO);
	void deleteVendorById(Long id);
}
