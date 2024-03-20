package edu.mirea.myinvest.repository;


import edu.mirea.myinvest.domain.model.PostUserRel;
import edu.mirea.myinvest.domain.model.PostUserRelId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostUserRelRepository extends JpaRepository<PostUserRel, PostUserRelId> {


    boolean existsByPostIdAndUserId(Long postId, Long userId);

    void deleteByPostIdAndUserId(Long postId, Long userId);
}