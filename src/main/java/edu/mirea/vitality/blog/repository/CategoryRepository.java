package edu.mirea.vitality.blog.repository;

import edu.mirea.vitality.blog.domain.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @interface CategoryRepository
 * @brief Этот интерфейс предоставляет методы для работы с сущностью Category в базе данных.
 */
public interface CategoryRepository extends JpaRepository<Category, Long> {

    /**
     * @brief Проверяет наличие категории с указанным заголовком.
     * @param value Заголовок категории.
     * @return true, если категория с указанным заголовком существует, иначе false.
     */
    boolean existsByTitle(String value);
}
