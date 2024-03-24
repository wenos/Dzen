package edu.mirea.vitality.blog.repository;

import edu.mirea.vitality.blog.domain.model.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @file PostRepository.java
 * @brief Этот файл содержит интерфейс PostRepository.
 */

/**
 * @interface PostRepository
 * @brief Этот интерфейс предоставляет методы для работы с сущностью Post в базе данных.
 */
@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    /**
     * @brief Получает страницу постов с учетом фильтрации по категории и заголовку.
     * @param categoryId Идентификатор категории (может быть null).
     * @param title Заголовок поста (может быть null).
     * @param pageable Интерфейс для создания пагинации.
     * @return Страница постов, отсортированных по дате создания в порядке убывания.
     */
    @Query("select p from Post p " +
            "where (:categoryId is null or p.category.id = :categoryId)" +
            "and (:title is null or p.title like %:title%) " +
            "order by p.createdAt desc")
    Page<Post> findAllWithPages(
            @Param("categoryId") Long categoryId,
            @Param("title") String title,
            Pageable pageable);

    /**
     * @brief Получает список всех постов автора с указанным идентификатором.
     * @param id Идентификатор автора.
     * @return Список постов автора.
     */
    List<Post> findAllByAuthorId(Long id);
}
