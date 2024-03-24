package edu.mirea.vitality.blog.mapper;


import edu.mirea.vitality.blog.domain.dto.category.CategoryRequest;
import edu.mirea.vitality.blog.domain.dto.category.CategoryResponse;
import edu.mirea.vitality.blog.domain.model.Category;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    Category toEntity(CategoryRequest categoryRequest);

    CategoryResponse toResponse(Category category);

    List<CategoryResponse> toResponse(List<Category> categories);
}
