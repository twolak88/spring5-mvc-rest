/**
 * 
 */
package com.twolak.springframework.spring5mvcrest.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.twolak.springframework.spring5mvcrest.domain.Category;

/**
 * @author twolak
 *
 */
public interface CategoryRepository extends JpaRepository<Category, Long> {
	Category findByName(String name);
}
