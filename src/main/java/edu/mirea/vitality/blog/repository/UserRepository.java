package edu.mirea.vitality.blog.repository;

import edu.mirea.vitality.blog.domain.model.Role;
import edu.mirea.vitality.blog.domain.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.Optional;

/**
 * @file UserRepository.java
 * @brief Этот файл содержит интерфейс UserRepository.
 */


/**
 * @interface UserRepository
 * @brief Этот интерфейс предоставляет методы для работы с сущностью User в базе данных.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * @brief Находит пользователя по имени пользователя.
     * @param username Имя пользователя.
     * @return Опциональный объект, содержащий пользователя, если найден.
     */
    Optional<User> findByUsername(String username);

    /**
     * @brief Проверяет существование пользователя по имени пользователя.
     * @param username Имя пользователя.
     * @return true, если пользователь существует, иначе false.
     */
    boolean existsByUsername(String username);

    /**
     * @brief Проверяет существование пользователя по адресу электронной почты.
     * @param email Адрес электронной почты.
     * @return true, если пользователь существует, иначе false.
     */
    boolean existsByEmail(String email);

    /**
     * @brief Получает страницу пользователей с учетом фильтрации.
     * @param id Идентификатор пользователя (может быть null).
     * @param username Имя пользователя (может быть null).
     * @param email Адрес электронной почты (может быть null).
     * @param role Роль пользователя (может быть null).
     * @param isBanned Признак блокировки пользователя (может быть null).
     * @param time Время для проверки блокировки пользователя (может быть null).
     * @param pageable Интерфейс для создания пагинации.
     * @return Страница пользователей, отфильтрованных в соответствии с заданными параметрами.
     */
    @Query("SELECT u FROM User u WHERE " +
            "(:username IS null OR u.username LIKE %:username%) " +
            "AND (:email IS null OR u.email LIKE %:email%) " +
            "AND (:role IS null OR u.role = :role) " +
            "AND (:isBanned IS null OR :isBanned = true AND u.bannedAt IS NOT NULL AND u.bannedAt > :time " +
            "OR (u.bannedAt IS NULL OR u.bannedAt <= :time) AND :isBanned = false) " +
            "AND (:id IS null OR u.id = :id)")
    Page<User> findAllWithFilter(
            @Param("id") Long id,
            @Param("username") String username,
            @Param("email") String email,
            @Param("role") Role role,
            @Param("isBanned") Boolean isBanned,
            @Param("time") OffsetDateTime time,
            Pageable pageable);
}
