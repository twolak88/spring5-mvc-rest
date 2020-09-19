/**
 * 
 */
package com.twolak.springframework.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.twolak.springframework.api.v1.model.CategoryDTO;
import com.twolak.springframework.domain.Category;

/**
 * @author twolak
 *
 */
@Mapper(componentModel = "spring")
public interface CategoryMapper {
	CategoryMapper INSTANCE = Mappers.getMapper(CategoryMapper.class);
	
	CategoryDTO categoryToCategoryDTO(Category category);
}
