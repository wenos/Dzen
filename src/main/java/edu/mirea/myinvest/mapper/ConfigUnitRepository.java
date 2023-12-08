package edu.mirea.myinvest.mapper;

import edu.mirea.myinvest.domain.model.system.SystemPropertyKey;
import edu.mirea.myinvest.domain.model.system.ConfigUnit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ConfigUnitRepository extends JpaRepository<ConfigUnit, String> {
    Optional<ConfigUnit> findByKey(SystemPropertyKey key);
}
