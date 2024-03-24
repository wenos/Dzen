package edu.mirea.vitality.blog.repository;

import edu.mirea.vitality.blog.domain.model.system.SystemPropertyKey;
import edu.mirea.vitality.blog.domain.model.system.ConfigUnit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @file ConfigUnitRepository.java
 * @brief Этот файл содержит интерфейс ConfigUnitRepository.
 */

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @interface ConfigUnitRepository
 * @brief Этот интерфейс предоставляет методы для работы с сущностью ConfigUnit в базе данных.
 */
@Repository
public interface ConfigUnitRepository extends JpaRepository<ConfigUnit, String> {

    /**
     * @brief Находит конфигурационный блок по его ключу системного свойства.
     * @param key Ключ системного свойства.
     * @return Опциональный объект, содержащий конфигурационный блок, если найден.
     */
    Optional<ConfigUnit> findByKey(SystemPropertyKey key);
}
