package edu.mirea.myinvest.repository;

import edu.mirea.myinvest.domain.model.SecurityType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SecurityTypeRepository extends JpaRepository<SecurityType, Long> {

}
