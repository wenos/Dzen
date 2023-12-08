package edu.mirea.myinvest.repository;

import edu.mirea.myinvest.domain.model.PostFileRel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostFileRelRepository extends JpaRepository<PostFileRel, Long> {
}
