package edu.mirea.vitality.blog.repository;

import edu.mirea.vitality.blog.domain.model.UploadedFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UploadedFileRepository extends JpaRepository<UploadedFile, UUID> {
}
