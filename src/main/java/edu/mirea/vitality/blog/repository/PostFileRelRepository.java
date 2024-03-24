package edu.mirea.vitality.blog.repository;

import edu.mirea.vitality.blog.domain.model.PostFileRel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @file PostFileRelRepository.java
 * @brief Этот файл содержит интерфейс PostFileRelRepository.
 */


/**
 * @interface PostFileRelRepository
 * @brief Этот интерфейс предоставляет методы для работы с сущностью PostFileRel в базе данных.
 */
@Repository
public interface PostFileRelRepository extends JpaRepository<PostFileRel, Long> {
}
