package edu.mirea.myinvest.service;


import edu.mirea.myinvest.domain.dto.category.CategoryRequest;
import edu.mirea.myinvest.domain.model.Category;
import edu.mirea.myinvest.domain.model.User;
import edu.mirea.myinvest.exception.category.CategoryNotFoundProblem;
import edu.mirea.myinvest.exception.category.CategoryNotUniqueTitleProblem;
import edu.mirea.myinvest.exception.user.ForbiddenAccessProblem;
import edu.mirea.myinvest.repository.CategoryRepository;
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

        categoryRepository.deleteById(id);
    }
}
