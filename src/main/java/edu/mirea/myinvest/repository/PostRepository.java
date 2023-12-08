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
//    @Query("select p from Post p where " +
//            "(:topicId is null or p.topic.id = :topicId) " +
//            "and (:postTypeId is null or p.postType.id = :postTypeId)")
//    Page<Post> findAllByTopicIdAndPostTypeId(
//            @Param("topicId") Long topicId,
//            @Param("postTypeId") Long postTypeId,
//            Pageable pageable);

    @Query("SELECT p FROM Post p where " + "(p.isNews = :isNews) ")
    Page<Post> findAllWithPages(@Param("isNews") Boolean isNews, Pageable pageable);


    List<Post> findAllByAuthorId(Long id);
}
