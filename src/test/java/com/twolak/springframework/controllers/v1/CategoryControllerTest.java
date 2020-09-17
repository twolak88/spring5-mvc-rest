package com.twolak.springframework.controllers.v1;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.anyString;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.twolak.springframework.api.v1.model.CategoryDTO;
import com.twolak.springframework.services.CategoryService;

@ExtendWith(MockitoExtension.class)
class CategoryControllerTest {

	public static final String CAT_NAME = "cat1";
	public static final String CAT_NAME2 = "cat2";
	
	@Mock
	private CategoryService categoryService;
	
	@InjectMocks
	private CategoryController categoryController;
	
	private MockMvc mockMvc;
	
	@BeforeEach
	void setUp() throws Exception {
		this.mockMvc = MockMvcBuilders.standaloneSetup(this.categoryController).build();
	}

	@Test
	public void testListAllCategories() throws Exception {
		CategoryDTO categoryDTO = new CategoryDTO();
		categoryDTO.setId(1L);
		categoryDTO.setName(CAT_NAME);
		
		CategoryDTO categoryDTO2 = new CategoryDTO();
		categoryDTO2.setId(2L);
		categoryDTO2.setName(CAT_NAME2);
		
		when(this.categoryService.getAllCategories()).thenReturn(Arrays.asList(categoryDTO, categoryDTO2));
		
		this.mockMvc.perform(get("/api/v1/categories")
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.categories", hasSize(2)));
		
		verify(this.categoryService, times(1)).getAllCategories();
		verifyNoMoreInteractions(this.categoryService);
	}
	
	@Test
	public void testGetByNameCategories() throws Exception {
		CategoryDTO categoryDTO = new CategoryDTO();
		categoryDTO.setId(1L);
		categoryDTO.setName(CAT_NAME);
		
		when(this.categoryService.getCategoryByName(anyString())).thenReturn(categoryDTO);
		
		this.mockMvc.perform(get("/api/v1/categories/cat1")
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.name", equalTo(CAT_NAME)));
		verify(this.categoryService, times(1)).getCategoryByName(anyString());
		verifyNoMoreInteractions(this.categoryService);
	}

}
