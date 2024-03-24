package edu.mirea.vitality.blog.service;


import edu.mirea.vitality.blog.domain.dto.category.CategoryRequest;
import edu.mirea.vitality.blog.domain.model.Category;
import edu.mirea.vitality.blog.domain.model.User;
import edu.mirea.vitality.blog.exception.category.CategoryNotFoundProblem;
import edu.mirea.vitality.blog.exception.category.CategoryNotUniqueTitleProblem;
import edu.mirea.vitality.blog.exception.user.ForbiddenAccessProblem;
import edu.mirea.vitality.blog.repository.CategoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @file CategoryService.java
 * @brief Этот файл содержит класс CategoryService.
 */


/**
 * @class CategoryService
 * @brief Этот класс предоставляет сервисные методы для работы с категориями.
 */
@Service
@AllArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final UserService userService;

    /**
     * @brief Создает новую категорию.
     * @param category Категория, которую нужно создать.
     * @return Созданная категория.
     * @throws ForbiddenAccessProblem Если текущий пользователь не является администратором.
     * @throws CategoryNotUniqueTitleProblem Если категория с таким заголовком уже существует.
     */
    public Category create(Category category) {
        User currentUser = userService.getCurrentUser();
        if (!currentUser.isAdmin()) {
            throw new ForbiddenAccessProblem();
        }

        if (categoryRepository.existsByTitle(category.getTitle())) {
            throw new CategoryNotUniqueTitleProblem(category.getTitle());
        }

        return categoryRepository.save(category);
    }

    /**
     * @brief Получает список всех категорий.
     * @return Список всех категорий.
     */
    public List<Category> getAll() {
        return categoryRepository.findAll();
    }

    private Optional<Category> findById(Long id) {
        return categoryRepository.findById(id);
    }

    /**
     * @brief Получает категорию по ее идентификатору.
     * @param id Идентификатор категории.
     * @return Категория с заданным идентификатором.
     * @throws CategoryNotFoundProblem Если категория не найдена.
     */
    public Category getById(Long id) {
        return findById(id).orElseThrow(() -> new CategoryNotFoundProblem(id));
    }

    /**
     * @brief Обновляет информацию о категории по ее идентификатору.
     * @param id Идентификатор категории.
     * @param category Новая информация о категории.
     * @return Обновленная категория.
     * @throws ForbiddenAccessProblem Если текущий пользователь не является администратором.
     * @throws CategoryNotUniqueTitleProblem Если категория с таким заголовком уже существует.
     */
    public Category updateById(Long id, CategoryRequest category) {
        User currentUser = userService.getCurrentUser();
        if (!currentUser.isAdmin()) {
            throw new ForbiddenAccessProblem();
        }

        Category foundCategory = getById(id);
        if (categoryRepository.existsByTitle(category.title()) && !foundCategory.getTitle().equals(category.title())) {
            throw new CategoryNotUniqueTitleProblem(category.title());
        }
        foundCategory.setTitle(category.title());

        return categoryRepository.save(foundCategory);
    }

    /**
     * @brief Удаляет категорию по ее идентификатору.
     * @param id Идентификатор категории.
     * @throws ForbiddenAccessProblem Если текущий пользователь не является администратором.
     * @throws CategoryNotFoundProblem Если категория не найдена.
     */
    public void deleteById(Long id) {
        User currentUser = userService.getCurrentUser();
        if (!currentUser.isAdmin()) {
            throw new ForbiddenAccessProblem();
        }
        Category category = getById(id);
        Category defaultCategory = getById(1L);

        category.getPosts().forEach(post -> post.setCategory(defaultCategory));

        categoryRepository.deleteById(id);
    }
}
