/**
 * 
 */
package com.twolak.springframework.services.impl;

import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.twolak.springframework.api.v1.model.VendorDTO;
import com.twolak.springframework.api.v1.model.VendorListDTO;
import com.twolak.springframework.domain.Vendor;
import com.twolak.springframework.mapper.VendorMapper;
import com.twolak.springframework.repositories.VendorRepository;
import com.twolak.springframework.services.ResourceNotFoundException;
import com.twolak.springframework.services.VendorService;

/**
 * @author twolak
 *
 */
@Service
public class VendorServiceImpl implements VendorService {
	
	private final VendorRepository vendorRepository;
	private final VendorMapper vendorMapper;
	

	public VendorServiceImpl(VendorRepository vendorRepository, VendorMapper vendorMapper) {
		this.vendorRepository = vendorRepository;
		this.vendorMapper = vendorMapper;
	}

	@Override
	public VendorListDTO getAllVendors() {
		return new VendorListDTO(this.vendorRepository.findAll().stream()
				.map(this.vendorMapper::vendorToVendorDTO).collect(Collectors.toList()));
	}

	@Override
	public VendorDTO getVendorById(Long id) {
		return this.vendorRepository.findById(id).map(this.vendorMapper::vendorToVendorDTO)
				.orElseThrow(()->new ResourceNotFoundException("Vendor doesn't exist for id: " + id));
	}

	@Override
	public VendorDTO createNewVendor(VendorDTO vendorDTO) {
		return saveVendor(this.vendorMapper.vendorDTOToVendor(vendorDTO));
	}

	@Override
	public VendorDTO updateVendor(Long id, VendorDTO vendorDTO) {
		Vendor vendor = this.vendorMapper.vendorDTOToVendor(vendorDTO);
		vendor.setId(id);
		return saveVendor(vendor);
	}

	@Override
	public VendorDTO patchVendor(Long id, VendorDTO vendorDTO) {
		return this.vendorRepository.findById(id).map(vendor -> {
			if (vendorDTO.getName() != null) {
				vendor.setName(vendorDTO.getName());
			}
			return this.vendorMapper.vendorToVendorDTO(this.vendorRepository.save(vendor));
		}).orElseThrow(() -> new ResourceNotFoundException("Customer for id: " + id + " not found"));
	}

	@Override
	public void deleteVendorById(Long id) {
		this.vendorRepository.deleteById(id);
	}
	
	public VendorDTO saveVendor(Vendor vendor) {
		Vendor savedVendor = this.vendorRepository.save(vendor);
		return this.vendorMapper.vendorToVendorDTO(savedVendor);
	}

}
