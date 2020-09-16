/**
 * 
 */
package com.twolak.springframework.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.twolak.springframework.domain.Category;

/**
 * @author twolak
 *
 */
public interface CategoryRepository extends JpaRepository<Category, Long> {
	Category findByName(String name);
}
