/**
 * 
 */
package com.twolak.springframework.services;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.BDDMockito.*;

import com.twolak.springframework.api.v1.model.VendorDTO;
import com.twolak.springframework.api.v1.model.VendorListDTO;
import com.twolak.springframework.controllers.v1.VendorController;
import com.twolak.springframework.domain.Vendor;
import com.twolak.springframework.mapper.VendorMapper;
import com.twolak.springframework.repositories.VendorRepository;
import com.twolak.springframework.services.impl.VendorServiceImpl;

/**
 * @author twolak
 *
 */
@ExtendWith(MockitoExtension.class)
class VendorServiceTestBDD {
	
	private static final String CUSTOMER_URL_PREFIX = VendorController.BASE_URL + "/";
	private static final Long ID_1 = 1L;
	private static final String NAME_1 = "CORP NAME";
	private static final Long ID_2 = 2L;
	private static final String NAME_2 = "CORP NAME2";
	
	@Mock
	private VendorRepository vendorRepository;
	
	private VendorService vendorService;
	
	@BeforeEach
	void setUp() throws Exception {
		this.vendorService = new VendorServiceImpl(vendorRepository, VendorMapper.INSANCE);
	}

	@Test
	void testGetAllVendors() {
		List<Vendor> vendors = Arrays.asList(getVendor1(), getVendor2());
		given(this.vendorRepository.findAll()).willReturn(vendors);
		
		VendorListDTO vendorListDTO = this.vendorService.getAllVendors();
		
		then(this.vendorRepository).should(times(1)).findAll();
		assertThat(vendorListDTO.getVendors().size(), is(equalTo(2)));
	}
	
	@Test
	void testGetVendorById() {
		Vendor vendor = getVendor1();
		given(this.vendorRepository.findById(anyLong())).willReturn(Optional.of(vendor));
		
		VendorDTO vendorDTO = this.vendorService.getVendorById(ID_1);
		
		then(this.vendorRepository).should(times(1)).findById(anyLong());
		assertThat(vendorDTO.getName(), is(equalTo(NAME_1)));
	}
	
	@Test
	void testGetVendorByIdNotFound() {
		given(this.vendorRepository.findById(anyLong())).willReturn(Optional.empty());
		
		assertThrows(ResourceNotFoundException.class, () -> {
			this.vendorService.getVendorById(ID_1);
			});
		
		then(this.vendorRepository).should(times(1)).findById(anyLong());
	}
	
	@Test
	void testCreateNewVendor() {
		VendorDTO vendorDTO = new VendorDTO();
		vendorDTO.setName(NAME_1);
		Vendor vendor = getVendor1();
		given(this.vendorRepository.save(any(Vendor.class))).willReturn(vendor);
		
		VendorDTO savedVendorDTO = this.vendorService.createNewVendor(vendorDTO);
		
		then(this.vendorRepository).should(times(1)).save(any(Vendor.class));
		assertThat(savedVendorDTO.getName(), is(equalTo(NAME_1)));
		assertThat(savedVendorDTO.getVendorUrl(), is(equalTo(CUSTOMER_URL_PREFIX + ID_1)));
	}
	
	@Test
	void testUpdateVendor() {
		VendorDTO vendorDTO = new VendorDTO();
		vendorDTO.setName(NAME_1);
		Vendor vendor = getVendor1();
		given(this.vendorRepository.save(any(Vendor.class))).willReturn(vendor);
		
		VendorDTO savedVendorDTO = this.vendorService.updateVendor(ID_1, vendorDTO);
		
		then(this.vendorRepository).should(times(1)).save(any(Vendor.class));
		assertThat(savedVendorDTO.getName(), is(equalTo(NAME_1)));
		assertThat(savedVendorDTO.getVendorUrl(), is(equalTo(CUSTOMER_URL_PREFIX + ID_1)));
	}
	
	@Test
	void testPatchVendor() {
		VendorDTO vendorDTO = new VendorDTO();
		vendorDTO.setName(NAME_1);
		Vendor vendor = getVendor1();
		
		given(this.vendorRepository.findById(anyLong())).willReturn(Optional.of(vendor));
		given(this.vendorRepository.save(any(Vendor.class))).willReturn(vendor);
		
		VendorDTO savedVendorDTO = this.vendorService.patchVendor(ID_1, vendorDTO);
		
		then(this.vendorRepository).should(times(1)).findById(anyLong());
		then(this.vendorRepository).should(times(1)).save(any(Vendor.class));
		assertThat(savedVendorDTO.getName(), is(equalTo(NAME_1)));
		assertThat(savedVendorDTO.getVendorUrl(), is(equalTo(CUSTOMER_URL_PREFIX + ID_1)));
	}
	
	@Test
	void testDeleteVendorById() {
		this.vendorService.deleteVendorById(ID_1);
		
		then(this.vendorRepository).should(times(1)).deleteById(anyLong());
	}
	
    private Vendor getVendor1() {
        Vendor vendor = new Vendor();
        vendor.setName(NAME_1);
        vendor.setId(ID_1);
        return vendor;
    }

    private Vendor getVendor2() {
        Vendor vendor = new Vendor();
        vendor.setName(NAME_2);
        vendor.setId(ID_2);
        return vendor;
    }
}
