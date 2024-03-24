package edu.mirea.vitality.blog.repository;


import edu.mirea.vitality.blog.domain.model.PostUserRel;
import edu.mirea.vitality.blog.domain.model.PostUserRelId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @file PostUserRelRepository.java
 * @brief Этот файл содержит интерфейс PostUserRelRepository.
 */



/**
 * @interface PostUserRelRepository
 * @brief Этот интерфейс предоставляет методы для работы с сущностью PostUserRel в базе данных.
 */
@Repository
public interface PostUserRelRepository extends JpaRepository<PostUserRel, PostUserRelId> {

    /**
     * @brief Проверяет существование связи между постом и пользователем.
     * @param postId Идентификатор поста.
     * @param userId Идентификатор пользователя.
     * @return true, если связь существует, иначе false.
     */
    boolean existsByPostIdAndUserId(Long postId, Long userId);

    /**
     * @brief Удаляет связь между постом и пользователем.
     * @param postId Идентификатор поста.
     * @param userId Идентификатор пользователя.
     */
    void deleteByPostIdAndUserId(Long postId, Long userId);
}
