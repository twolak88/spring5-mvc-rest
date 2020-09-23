/**
 * 
 */
package com.twolak.springframework.spring5mvcrest.controllers.v1;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.twolak.springframework.spring5mvcrest.api.v1.model.CategoryDTO;
import com.twolak.springframework.spring5mvcrest.api.v1.model.CategoryListDTO;
import com.twolak.springframework.spring5mvcrest.services.CategoryService;

import io.swagger.annotations.Api;

/**
 * @author twolak
 *
 */
@Api(tags = {"CategoryController"})
@RestController
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
	@ResponseStatus(HttpStatus.OK)
	public CategoryListDTO getAllCategories(){
		return new CategoryListDTO(this.categoryService.getAllCategories());
	}
	
	@GetMapping(NAME_URL_PROPERTY)
	@ResponseStatus(HttpStatus.OK)
	public CategoryDTO getCategoryByName(@PathVariable(NAME_PROPERTY) String name) {
		return this.categoryService.getCategoryByName(name);
	}
	
}
