package edu.mirea.myinvest.controller;


import edu.mirea.myinvest.domain.dto.category.CategoryRequest;
import edu.mirea.myinvest.domain.dto.category.CategoryResponse;
import edu.mirea.myinvest.domain.model.Category;
import edu.mirea.myinvest.mapper.CategoryMapper;
import edu.mirea.myinvest.service.CategoryService;
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


@RestController
@RequiredArgsConstructor
@RequestMapping("/category")
public class CategoryController {

    private final CategoryService categoryService;

    private final CategoryMapper categoryMapper;

    @PostMapping
    @Operation(summary = "Создание новой категории")
    @ResponseStatus(value = HttpStatus.CREATED)
    public CategoryResponse create(@RequestBody @Valid CategoryRequest request) {
        Category entity = categoryMapper.toEntity(request);
        Category created = categoryService.create(entity);
        return categoryMapper.toResponse(created);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получение категории по categoryId")
    public CategoryResponse getById(@PathVariable Long id) {
        Category found = categoryService.getById(id);
        return categoryMapper.toResponse(found);
    }


    @GetMapping
    @Operation(summary = "Получение списка всех категорий")
    public List<CategoryResponse> getAll() {
        List<Category> found = categoryService.getAll();
        return categoryMapper.toResponse(found);
    }




    @PutMapping("/{id}")
    @Operation(summary = "Обновлении категории")
    public CategoryResponse update(@PathVariable Long id, @RequestBody @Valid CategoryRequest request) {
        Category category = categoryService.updateById(id, request);
        return categoryMapper.toResponse(category);
    }


    @DeleteMapping("/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @Operation(summary = "Удаление категории")
    public void delete(@PathVariable Long id) {
        categoryService.deleteById(id);
    }


}
