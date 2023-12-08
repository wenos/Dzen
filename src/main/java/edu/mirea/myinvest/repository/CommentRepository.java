package edu.mirea.myinvest.repository;

import edu.mirea.myinvest.domain.model.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    Page<Comment> findAllByPostId(Long postId, Pageable pageable);

    @Modifying
    @Query("DELETE FROM Comment c WHERE c.createdAt <= :cutoffDate")
    void deleteOldComments(@Param("cutoffDate") OffsetDateTime cutoffDate);

    void deleteAllByAuthorId(Long id);
}
