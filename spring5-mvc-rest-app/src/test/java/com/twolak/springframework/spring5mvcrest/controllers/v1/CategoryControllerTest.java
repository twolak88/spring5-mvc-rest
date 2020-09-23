package com.twolak.springframework.spring5mvcrest.controllers.v1;

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

import com.twolak.springframework.spring5mvcrest.api.v1.model.CategoryDTO;
import com.twolak.springframework.spring5mvcrest.services.CategoryService;
import com.twolak.springframework.spring5mvcrest.services.ResourceNotFoundException;

@ExtendWith(MockitoExtension.class)
public class CategoryControllerTest {
	
	private static final long CAT_ID_2 = 2L;
	private static final long CAT_ID_1 = 1L;
	public static final String BASE_URL = CategoryController.BASE_URL;
	public static final String CAT_NAME = "cat1";
	public static final String CAT_NAME2 = "cat2";
	
	@Mock
	private CategoryService categoryService;
	
	@InjectMocks
	private CategoryController categoryController;
	
	private MockMvc mockMvc;
	
	@BeforeEach
	void setUp() throws Exception {
		this.mockMvc = MockMvcBuilders.standaloneSetup(this.categoryController).setControllerAdvice(new RestResponseEntityExceptionHandler()).build();
	}

	@Test
	public void testListAllCategories() throws Exception {
		CategoryDTO categoryDTO = new CategoryDTO();
		categoryDTO.setId(CAT_ID_1);
		categoryDTO.setName(CAT_NAME);
		
		CategoryDTO categoryDTO2 = new CategoryDTO();
		categoryDTO2.setId(CAT_ID_2);
		categoryDTO2.setName(CAT_NAME2);
		
		when(this.categoryService.getAllCategories()).thenReturn(Arrays.asList(categoryDTO, categoryDTO2));
		
		this.mockMvc.perform(get(BASE_URL)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.categories", hasSize(2)));
		
		verify(this.categoryService, times(1)).getAllCategories();
		verifyNoMoreInteractions(this.categoryService);
	}
	
	@Test
	public void testGetByNameCategories() throws Exception {
		CategoryDTO categoryDTO = new CategoryDTO();
		categoryDTO.setId(CAT_ID_1);
		categoryDTO.setName(CAT_NAME);
		
		when(this.categoryService.getCategoryByName(anyString())).thenReturn(categoryDTO);
		
		this.mockMvc.perform(get(BASE_URL + "/" + CAT_NAME)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.name", equalTo(CAT_NAME)));
		verify(this.categoryService, times(1)).getCategoryByName(anyString());
		verifyNoMoreInteractions(this.categoryService);
	}
	
	@Test
	public void testGetByNameNotFound() throws Exception {
		when(this.categoryService.getCategoryByName(anyString())).thenThrow(ResourceNotFoundException.class);
		
		this.mockMvc.perform(get(BASE_URL + "/" + CAT_NAME)
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isNotFound());
	}
}
