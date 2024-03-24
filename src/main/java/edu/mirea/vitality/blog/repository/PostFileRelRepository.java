package edu.mirea.vitality.blog.repository;

import edu.mirea.vitality.blog.domain.model.PostFileRel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostFileRelRepository extends JpaRepository<PostFileRel, Long> {
}
