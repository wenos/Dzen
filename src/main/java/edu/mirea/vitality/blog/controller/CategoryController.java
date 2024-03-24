package edu.mirea.vitality.blog.controller;


import edu.mirea.vitality.blog.domain.dto.category.CategoryRequest;
import edu.mirea.vitality.blog.domain.dto.category.CategoryResponse;
import edu.mirea.vitality.blog.domain.model.Category;
import edu.mirea.vitality.blog.mapper.CategoryMapper;
import edu.mirea.vitality.blog.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;



/**
 * @class CategoryController
 * @brief Этот класс представляет собой контроллер для управления категориями.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/category")
public class CategoryController {

    private final CategoryService categoryService;

    private final CategoryMapper categoryMapper;

    /**
     * @brief Создает новую категорию.
     * @param request Данные запроса категории.
     * @return Созданный ответ категории.
     */
    @PostMapping
    @Operation(summary = "Создание новой категории")
    @ResponseStatus(value = HttpStatus.CREATED)
    public CategoryResponse create(@RequestBody @Valid CategoryRequest request) {
        Category entity = categoryMapper.toEntity(request);
        Category created = categoryService.create(entity);
        return categoryMapper.toResponse(created);
    }

    /**
     * @brief Получает категорию по ее идентификатору.
     * @param id Идентификатор категории.
     * @return Ответ категории.
     */
    @GetMapping("/{id}")
    @Operation(summary = "Получение категории по categoryId")
    public CategoryResponse getById(@PathVariable Long id) {
        Category found = categoryService.getById(id);
        return categoryMapper.toResponse(found);
    }

    /**
     * @brief Получает все категории.
     * @return Список ответов категорий.
     */
    @GetMapping
    @Operation(summary = "Получение списка всех категорий")
    public List<CategoryResponse> getAll() {
        List<Category> found = categoryService.getAll();
        return categoryMapper.toResponse(found);
    }

    /**
     * @brief Обновляет категорию по ее идентификатору.
     * @param id Идентификатор категории.
     * @param request Данные запроса категории.
     * @return Обновленный ответ категории.
     */
    @PutMapping("/{id}")
    @Operation(summary = "Обновлении категории")
    public CategoryResponse update(@PathVariable Long id, @RequestBody @Valid CategoryRequest request) {
        Category category = categoryService.updateById(id, request);
        return categoryMapper.toResponse(category);
    }

    /**
     * @brief Удаляет категорию по ее идентификатору.
     * @param id Идентификатор категории.
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @Operation(summary = "Удаление категории")
    public void delete(@PathVariable Long id) {
        categoryService.deleteById(id);
    }
}

