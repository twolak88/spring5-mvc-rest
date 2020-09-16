/**
 * 
 */
package com.twolak.springframework.bootstrap;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.twolak.springframework.domain.Category;
import com.twolak.springframework.repositories.CategoryRepository;

import lombok.extern.slf4j.Slf4j;

/**
 * @author twolak
 *
 */
@Slf4j
@Component
public class Bootstrap implements CommandLineRunner {

	private final CategoryRepository categoryRepository;
	
	public Bootstrap(CategoryRepository categoryRepository) {
		this.categoryRepository = categoryRepository;
	}

	@Override
	public void run(String... args) throws Exception {
		createCategory("Fruits");
		createCategory("Dried");
		createCategory("Fresh");
		createCategory("Exotic");
		createCategory("Nuts");
		
		log.info("Data loaded: " + this.categoryRepository.count());
	}
	
	public void createCategory(String name) {
		Category category = new Category();
		category.setName(name);
		this.categoryRepository.save(category);
	}
}
