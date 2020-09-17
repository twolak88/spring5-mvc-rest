/**
 * 
 */
package com.twolak.springframework.bootstrap;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.twolak.springframework.domain.Category;
import com.twolak.springframework.domain.Customer;
import com.twolak.springframework.repositories.CategoryRepository;
import com.twolak.springframework.repositories.CustomerRepository;

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
	
	public Bootstrap(CategoryRepository categoryRepository, CustomerRepository customerRepository) {
		this.categoryRepository = categoryRepository;
		this.customerRepository = customerRepository;
	}

	@Override
	public void run(String... args) throws Exception {
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
		createCustomer("Mike", "Weston");
		createCustomer("Sam", "Axe");
	}
	
	private void createCategory(String name) {
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
}
