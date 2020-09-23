package com.twolak.springframework.spring5mvcrest.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.twolak.springframework.spring5mvcrest.api.v1.model.VendorDTO;
import com.twolak.springframework.spring5mvcrest.api.v1.model.VendorListDTO;
import com.twolak.springframework.spring5mvcrest.controllers.v1.VendorController;
import com.twolak.springframework.spring5mvcrest.domain.Vendor;
import com.twolak.springframework.spring5mvcrest.mapper.VendorMapper;
import com.twolak.springframework.spring5mvcrest.repositories.VendorRepository;
import com.twolak.springframework.spring5mvcrest.services.impl.VendorServiceImpl;

@ExtendWith(MockitoExtension.class)
class VendorServiceTest {
	private static final String CUSTOMER_URL_PREFIX = VendorController.BASE_URL + "/";
	private static final Long ID = 1L;
	private static final String NAME = "CORP NAME";
	private static final String PATCHED_NAME = "OTHER NAME";
	
	
	@Mock
	private VendorRepository vendorRepository;
	
	private VendorService vendorService;
	
	@BeforeEach
	void setUp() throws Exception {
		this.vendorService = new VendorServiceImpl(vendorRepository, VendorMapper.INSANCE);
	}

	@Test
	void testGetAllVendors() {
		when(this.vendorRepository.findAll()).thenReturn(Arrays.asList(new Vendor(), new Vendor()));
		
		VendorListDTO vendorDTOs = this.vendorService.getAllVendors();
		
		assertEquals(2, vendorDTOs.getVendors().size());
		verify(this.vendorRepository, times(1)).findAll();
		verifyNoMoreInteractions(this.vendorRepository);
	}
	
	@Test
	void testGetVendorById() {
		Vendor vendor = new Vendor();
		vendor.setId(ID);
		vendor.setName(NAME);
		
		when(this.vendorRepository.findById(anyLong())).thenReturn(Optional.of(vendor));
		
		VendorDTO vendorDTO = this.vendorService.getVendorById(ID);
		
		assertNotNull(vendorDTO);
		assertEquals(NAME, vendorDTO.getName());
		assertEquals(CUSTOMER_URL_PREFIX + ID, vendorDTO.getVendorUrl());
		verify(this.vendorRepository, times(1)).findById(anyLong());
		verifyNoMoreInteractions(this.vendorRepository);
	}
	
	@Test
	void testCreateNewVendor() {
		VendorDTO vendorDTO = new VendorDTO();
		vendorDTO.setName(NAME);
		
		Vendor savedVendor = new Vendor();
		savedVendor.setName(NAME);
		savedVendor.setId(ID);
		
		when(this.vendorRepository.save(any(Vendor.class))).thenReturn(savedVendor);
		
		VendorDTO savedVendorDTO = this.vendorService.createNewVendor(vendorDTO);
		
		assertEquals(savedVendorDTO.getName(), savedVendorDTO.getName());
		assertEquals(CUSTOMER_URL_PREFIX + ID, savedVendorDTO.getVendorUrl());
		verify(this.vendorRepository, times(1)).save(any(Vendor.class));
		verifyNoMoreInteractions(this.vendorRepository);
	}
	
	@Test
	void testUpdateVendor() {
		VendorDTO vendorDTO = new VendorDTO();
		vendorDTO.setName(NAME);
		
		Vendor savedVendor = new Vendor();
		savedVendor.setName(NAME);
		savedVendor.setId(ID);
		
		when(this.vendorRepository.save(any(Vendor.class))).thenReturn(savedVendor);
		
		VendorDTO savedVendorDTO = this.vendorService.updateVendor(ID, vendorDTO);
		
		assertEquals(savedVendorDTO.getName(), savedVendorDTO.getName());
		assertEquals(CUSTOMER_URL_PREFIX + ID, savedVendorDTO.getVendorUrl());
		verify(this.vendorRepository, times(1)).save(any(Vendor.class));
		verifyNoMoreInteractions(this.vendorRepository);
	}
	
	@Test
	void testPatchVendor() {
		VendorDTO vendorDTO = new VendorDTO();
		vendorDTO.setName(PATCHED_NAME);
		
		Vendor foundVendor = new Vendor();
		foundVendor.setName(NAME);
		foundVendor.setId(ID);
		
		Vendor savedVendor = new Vendor();
		savedVendor.setName(PATCHED_NAME);
		savedVendor.setId(ID);
		
		when(this.vendorRepository.findById(anyLong())).thenReturn(Optional.of(foundVendor));
		when(this.vendorRepository.save(any(Vendor.class))).thenReturn(savedVendor);
		
		VendorDTO savedVendorDTO = this.vendorService.patchVendor(ID, vendorDTO);
		
		assertEquals(savedVendor.getName(), savedVendorDTO.getName());
		assertEquals(CUSTOMER_URL_PREFIX + ID, savedVendorDTO.getVendorUrl());
		verify(this.vendorRepository, times(1)).save(any(Vendor.class));
		verify(this.vendorRepository, times(1)).findById(anyLong());
		verifyNoMoreInteractions(this.vendorRepository);
	}
	
	@Test
	void testDeleteVendorById() {
		this.vendorService.deleteVendorById(ID);
		
		verify(this.vendorRepository, times(1)).deleteById(anyLong());
		verifyNoMoreInteractions(this.vendorRepository);
	}
}
