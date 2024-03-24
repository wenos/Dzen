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

@Service
@AllArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    private final UserService userService;

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

    public List<Category> getAll() {
        return categoryRepository.findAll();
    }

    private Optional<Category> findById(Long id) {
        return categoryRepository.findById(id);
    }

    public Category getById(Long id) {
        return findById(id).orElseThrow(() -> new CategoryNotFoundProblem(id));
    }

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
