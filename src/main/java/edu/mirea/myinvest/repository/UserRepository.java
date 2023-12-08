package edu.mirea.myinvest.repository;

import edu.mirea.myinvest.domain.model.Role;
import edu.mirea.myinvest.domain.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

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