/**
 * 
 */
package com.twolak.springframework.spring5mvcrest.services;

import java.util.List;

import com.twolak.springframework.spring5mvcrest.api.v1.model.CategoryDTO;

/**
 * @author twolak
 *
 */
public interface CategoryService {
	List<CategoryDTO> getAllCategories();
	CategoryDTO getCategoryByName(String name);
}
