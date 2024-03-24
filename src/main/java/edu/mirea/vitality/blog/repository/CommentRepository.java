package edu.mirea.vitality.blog.repository;

import edu.mirea.vitality.blog.domain.model.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;

/**
 * @interface CommentRepository
 * @brief Этот интерфейс предоставляет методы для работы с сущностью Comment в базе данных.
 */
@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    /**
     * @brief Получает страницу комментариев для указанного идентификатора поста.
     * @param postId Идентификатор поста.
     * @param pageable Интерфейс для создания пагинации.
     * @return Страница комментариев.
     */
    Page<Comment> findAllByPostId(Long postId, Pageable pageable);

    /**
     * @brief Удаляет старые комментарии, созданные до указанной даты.
     * @param cutoffDate Дата, до которой нужно удалить комментарии.
     */
    @Modifying
    @Query("DELETE FROM Comment c WHERE c.createdAt <= :cutoffDate")
    void deleteOldComments(@Param("cutoffDate") OffsetDateTime cutoffDate);

    /**
     * @brief Удаляет все комментарии автора с указанным идентификатором.
     * @param id Идентификатор автора комментариев.
     */
    void deleteAllByAuthorId(Long id);
}
