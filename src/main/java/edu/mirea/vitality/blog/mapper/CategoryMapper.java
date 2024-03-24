package edu.mirea.vitality.blog.mapper;


import edu.mirea.vitality.blog.domain.dto.category.CategoryRequest;
import edu.mirea.vitality.blog.domain.dto.category.CategoryResponse;
import edu.mirea.vitality.blog.domain.model.Category;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * @file CategoryMapper.java
 * @brief Этот файл содержит интерфейс CategoryMapper.
 */

/**
 * @interface CategoryMapper
 * @brief Этот интерфейс предоставляет методы для преобразования объектов категорий между DTO и сущностью.
 */
@Mapper(componentModel = "spring")
public interface CategoryMapper {

    /**
     * @brief Преобразует объект запроса категории в сущность категории.
     * @param categoryRequest Запрос категории.
     * @return Сущность категории.
     */
    Category toEntity(CategoryRequest categoryRequest);

    /**
     * @brief Преобразует сущность категории в объект ответа категории.
     * @param category Сущность категории.
     * @return Ответ категории.
     */
    CategoryResponse toResponse(Category category);

    /**
     * @brief Преобразует список сущностей категорий в список объектов ответа категорий.
     * @param categories Список сущностей категорий.
     * @return Список ответов категорий.
     */
    List<CategoryResponse> toResponse(List<Category> categories);
}
