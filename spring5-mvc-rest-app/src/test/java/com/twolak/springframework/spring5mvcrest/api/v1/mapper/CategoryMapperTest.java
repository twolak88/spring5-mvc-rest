/**
 * 
 */
package com.twolak.springframework.spring5mvcrest.api.v1.mapper;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.twolak.springframework.spring5mvcrest.api.v1.model.CategoryDTO;
import com.twolak.springframework.spring5mvcrest.domain.Category;
import com.twolak.springframework.spring5mvcrest.mapper.CategoryMapper;

/**
 * @author twolak
 *
 */
class CategoryMapperTest {
	
	private static final String NAME = "Fruits";
	private static final long ID = 1L;
	
	
	private CategoryMapper categoryMapper = CategoryMapper.INSTANCE;
	
	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
	void testCategoryToCategoryDTO() {
		Category category = new Category();
		category.setId(ID);
		category.setName(NAME);
		
		CategoryDTO categoryDTO = this.categoryMapper.categoryToCategoryDTO(category);
		
		assertEquals(ID, categoryDTO.getId());
		assertEquals(NAME, categoryDTO.getName());
	}

}
