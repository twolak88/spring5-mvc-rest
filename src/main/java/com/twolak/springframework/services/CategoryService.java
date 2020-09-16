/**
 * 
 */
package com.twolak.springframework.services;

import java.util.List;

import com.twolak.springframework.api.v1.model.CategoryDTO;

/**
 * @author twolak
 *
 */
public interface CategoryService {
	List<CategoryDTO> getAllCategories();
	CategoryDTO getCategoryByName(String name);
}
