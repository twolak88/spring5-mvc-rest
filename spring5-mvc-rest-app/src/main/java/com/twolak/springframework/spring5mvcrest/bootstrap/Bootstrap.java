/**
 * 
 */
package com.twolak.springframework.spring5mvcrest.bootstrap;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.twolak.springframework.spring5mvcrest.domain.Category;
import com.twolak.springframework.spring5mvcrest.domain.Customer;
import com.twolak.springframework.spring5mvcrest.domain.Vendor;
import com.twolak.springframework.spring5mvcrest.repositories.CategoryRepository;
import com.twolak.springframework.spring5mvcrest.repositories.CustomerRepository;
import com.twolak.springframework.spring5mvcrest.repositories.VendorRepository;

import lombok.extern.slf4j.Slf4j;

/**
 * @author twolak
 *
 */
@Slf4j
@Component
public class Bootstrap implements CommandLineRunner {

	private final CategoryRepository categoryRepository;
	private final CustomerRepository customerRepository;
	private final VendorRepository vendorRepository;
	
	public Bootstrap(CategoryRepository categoryRepository, CustomerRepository customerRepository, VendorRepository vendorRepository) {
		this.categoryRepository = categoryRepository;
		this.customerRepository = customerRepository;
		this.vendorRepository = vendorRepository;
	}

	@Override
	public void run(String... args) throws Exception {
		loadVendors();
		log.info("Vendor loaded: " + this.vendorRepository.count());
		loadCustomers();
		log.info("Customers loaded: " + this.customerRepository.count());
		loadCategories();
		log.info("Categories loaded: " + this.categoryRepository.count());
		
	}
	
	private void loadCategories() {
		createCategory("Fruits");
		createCategory("Dried");
		createCategory("Fresh");
		createCategory("Exotic");
		createCategory("Nuts");
	}
	
	private void loadCustomers() {
		if (this.customerRepository.count() > 0) return;
		createCustomer("Mike", "Weston");
		createCustomer("Sam", "Axe");
	}
	
	private void loadVendors() {
		if (this.vendorRepository.count() > 0) return;
		createVendor("Fruits LTD");
		createVendor("Breakfast LTD");
		createVendor("Shop LTD");
		createVendor("Ven LTD");
	}
	
	private void createCategory(String name) {
		if (this.categoryRepository.findByName(name) != null) return;
		Category category = new Category();
		category.setName(name);
		this.categoryRepository.save(category);
	}
	
	private void createCustomer(String firstname, String lastname) {
		Customer customer = new Customer();
		customer.setFirstname(firstname);
		customer.setLastname(lastname);
		this.customerRepository.save(customer);
	}
	
	private void createVendor(String name) {
		Vendor vendor = new Vendor();
		vendor.setName(name);
		this.vendorRepository.save(vendor);
	}
}
