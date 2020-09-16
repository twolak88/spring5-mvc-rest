/**
 * 
 */
package com.twolak.springframework.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.anyString;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.twolak.springframework.api.v1.mapper.CategoryMapper;
import com.twolak.springframework.api.v1.model.CategoryDTO;
import com.twolak.springframework.domain.Category;
import com.twolak.springframework.repositories.CategoryRepository;
import com.twolak.springframework.services.impl.CategoryServiceImpl;

/**
 * @author twolak
 *
 */
@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {
	
	private static final String CAT_NAME = "Fruits";
	private static final long CAT_ID = 1L;
	
	private CategoryService categoryService;
	
	@Mock
	private CategoryRepository categoryRepository;
	
	@BeforeEach
	void setUp() throws Exception {
		categoryService = new CategoryServiceImpl(categoryRepository, CategoryMapper.INSTANCE);
	}

	@Test
	void testGetAllCategories() {		
		when(this.categoryRepository.findAll()).thenReturn(Arrays.asList(new Category(), new Category()));
		
		List<CategoryDTO> foundCategories = this.categoryService.getAllCategories();
		
		assertEquals(2, foundCategories.size());
		verify(this.categoryRepository, times(1)).findAll();
		verifyNoMoreInteractions(this.categoryRepository);
	}
	
	@Test
	void testGetCategoryByName() {
		Category category = new Category();
		category.setId(CAT_ID);
		category.setName(CAT_NAME);
		
		when(this.categoryRepository.findByName(anyString())).thenReturn(category);
		
		CategoryDTO foundCategoryDTO = this.categoryService.getCategoryByName(CAT_NAME);
		
		assertNotNull(foundCategoryDTO);
		assertEquals(CAT_NAME, foundCategoryDTO.getName());
		assertEquals(CAT_ID, foundCategoryDTO.getId());
		verify(this.categoryRepository, times(1)).findByName(anyString());
		verifyNoMoreInteractions(this.categoryRepository);
	}

}
