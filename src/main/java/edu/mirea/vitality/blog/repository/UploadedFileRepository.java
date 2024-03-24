package edu.mirea.vitality.blog.repository;

import edu.mirea.vitality.blog.domain.model.UploadedFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * @file UploadedFileRepository.java
 * @brief Этот файл содержит интерфейс UploadedFileRepository.
 */


/**
 * @interface UploadedFileRepository
 * @brief Этот интерфейс предоставляет методы для работы с сущностью UploadedFile в базе данных.
 */
@Repository
public interface UploadedFileRepository extends JpaRepository<UploadedFile, UUID> {
}
