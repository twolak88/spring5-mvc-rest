/**
 * 
 */
package com.twolak.springframework.controllers.v1;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.twolak.springframework.api.v1.model.CategoryDTO;
import com.twolak.springframework.api.v1.model.CategoryListDTO;
import com.twolak.springframework.services.CategoryService;

/**
 * @author twolak
 *
 */
@Controller
@RequestMapping(CategoryController.BASE_URL)
public class CategoryController {
	
	public static final String BASE_URL = "/api/v1/categories";
	public static final String NAME_PROPERTY = "name";
	public static final String NAME_URL_PROPERTY = "{" + NAME_PROPERTY + "}";
	
	private final CategoryService categoryService;

	public CategoryController(CategoryService categoryService) {
		this.categoryService = categoryService;
	}
	
	@GetMapping
	public ResponseEntity<CategoryListDTO> getAllCategories(){
		return new ResponseEntity<CategoryListDTO>(new CategoryListDTO(this.categoryService.getAllCategories()),HttpStatus.OK);
	}
	
	@GetMapping(NAME_URL_PROPERTY)
	public ResponseEntity<CategoryDTO> getCategoryByName(@PathVariable(NAME_PROPERTY) String name) {
		return new ResponseEntity<CategoryDTO>(this.categoryService.getCategoryByName(name), HttpStatus.OK);
	}
	
}
