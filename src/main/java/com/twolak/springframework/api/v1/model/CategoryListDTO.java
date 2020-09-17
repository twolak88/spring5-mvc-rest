/**
 * 
 */
package com.twolak.springframework.api.v1.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author twolak
 *
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryListDTO {
	List<CategoryDTO> categories;
}
