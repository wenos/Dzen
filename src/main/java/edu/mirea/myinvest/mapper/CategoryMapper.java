package edu.mirea.myinvest.mapper;


import edu.mirea.myinvest.domain.dto.category.CategoryRequest;
import edu.mirea.myinvest.domain.dto.category.CategoryResponse;
import edu.mirea.myinvest.domain.model.Category;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    Category toEntity(CategoryRequest categoryRequest);

    CategoryResponse toResponse(Category category);

    List<CategoryResponse> toResponse(List<Category> categories);
}
