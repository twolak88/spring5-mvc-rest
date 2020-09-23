/**
 * 
 */
package com.twolak.springframework.spring5mvcrest.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.twolak.springframework.spring5mvcrest.api.v1.model.CategoryDTO;
import com.twolak.springframework.spring5mvcrest.domain.Category;

/**
 * @author twolak
 *
 */
@Mapper(componentModel = "spring")
public interface CategoryMapper {
	CategoryMapper INSTANCE = Mappers.getMapper(CategoryMapper.class);
	
	CategoryDTO categoryToCategoryDTO(Category category);
}
