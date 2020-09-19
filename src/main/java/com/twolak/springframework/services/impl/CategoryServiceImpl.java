/**
 * 
 */
package com.twolak.springframework.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.twolak.springframework.api.v1.model.CategoryDTO;
import com.twolak.springframework.mapper.CategoryMapper;
import com.twolak.springframework.repositories.CategoryRepository;
import com.twolak.springframework.services.CategoryService;

/**
 * @author twolak
 *
 */
@Service
public class CategoryServiceImpl implements CategoryService {

	private final CategoryRepository categoryRepository;
	private final CategoryMapper categoryMapper;
	
	public CategoryServiceImpl(CategoryRepository categoryRepository, CategoryMapper categoryMapper) {
		this.categoryRepository = categoryRepository;
		this.categoryMapper = categoryMapper;
	}

	@Override
	public List<CategoryDTO> getAllCategories() {
		return this.categoryRepository.findAll()
							.stream()
							.map(this.categoryMapper::categoryToCategoryDTO)
							.collect(Collectors.toList());
	}

	@Override
	public CategoryDTO getCategoryByName(String name) {
		return this.categoryMapper.categoryToCategoryDTO(this.categoryRepository.findByName(name));
	}

}
