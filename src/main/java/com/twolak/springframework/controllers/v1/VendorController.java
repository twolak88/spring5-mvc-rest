/**
 * 
 */
package com.twolak.springframework.controllers.v1;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.twolak.springframework.api.v1.model.VendorDTO;
import com.twolak.springframework.api.v1.model.VendorListDTO;
import com.twolak.springframework.services.VendorService;

/**
 * @author twolak
 *
 */
@RestController
@RequestMapping(VendorController.BASE_URL)
public class VendorController {
	public static final String BASE_URL = "/api/v1/vendors";
	private static final String ID_PROPERTY = "id";
	private static final String ID_URL_PROPERTY = "{" + ID_PROPERTY + "}";
	
	private final VendorService vendorService;

	public VendorController(VendorService vendorService) {
		this.vendorService = vendorService;
	}
	
	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public VendorListDTO getAllVendors() {
		return this.vendorService.getAllVendors();
	}
	
	@GetMapping(ID_URL_PROPERTY)
	@ResponseStatus(HttpStatus.OK)
	public VendorDTO getVendorById(@PathVariable(ID_PROPERTY) Long id) {
		return this.vendorService.getVendorById(id);
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public VendorDTO createNewVendor(@RequestBody VendorDTO vendorDTO) {
		return this.vendorService.createNewVendor(vendorDTO);
	}
	
	@PutMapping(ID_URL_PROPERTY)
	@ResponseStatus(HttpStatus.OK)
	public VendorDTO updateVendor(@PathVariable(ID_PROPERTY) Long id, @RequestBody VendorDTO vendorDTO) {
		return this.vendorService.updateVendor(id, vendorDTO);
	}
	
	@PatchMapping(ID_URL_PROPERTY)
	@ResponseStatus(HttpStatus.OK)
	public VendorDTO patchVendor(@PathVariable(ID_PROPERTY) Long id, @RequestBody VendorDTO vendorDTO) {
		return this.vendorService.patchVendor(id, vendorDTO);
	}
	
	@DeleteMapping(ID_URL_PROPERTY)
	@ResponseStatus(HttpStatus.OK)
	public void deleteVendor(@PathVariable(ID_PROPERTY) Long id) {
		this.vendorService.deleteVendorById(id);
	}
}
