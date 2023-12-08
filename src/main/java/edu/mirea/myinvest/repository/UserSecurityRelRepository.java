package edu.mirea.myinvest.repository;

import edu.mirea.myinvest.domain.model.UserSecurityRel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserSecurityRelRepository extends JpaRepository<UserSecurityRel, Long> {

    boolean existsByUserIdAndSecurityId(Long userId, Long securityId);

    void deleteByUserIdAndSecurityId(Long userId, Long securityId);
}
