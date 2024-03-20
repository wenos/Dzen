package edu.mirea.myinvest.repository;

import edu.mirea.myinvest.domain.model.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    @Query("select p from Post p " +
            "where (:categoryId is null or p.category.id = :categoryId)" +
            "and (:title is null or p.title like %:title%)")
    Page<Post> findAllWithPages(
            @Param("categoryId") Long categoryId,
            @Param("title") String title,
            Pageable pageable);

    List<Post> findAllByAuthorId(Long id);
}
