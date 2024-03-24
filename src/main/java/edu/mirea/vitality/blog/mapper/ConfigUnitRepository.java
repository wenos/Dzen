package edu.mirea.vitality.blog.mapper;

import edu.mirea.vitality.blog.domain.model.system.SystemPropertyKey;
import edu.mirea.vitality.blog.domain.model.system.ConfigUnit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ConfigUnitRepository extends JpaRepository<ConfigUnit, String> {
    Optional<ConfigUnit> findByKey(SystemPropertyKey key);
}
