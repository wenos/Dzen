package edu.mirea.myinvest.repository;

import edu.mirea.myinvest.domain.model.Security;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SecurityRepository extends JpaRepository<Security, Long> {
    @Query("SELECT s FROM Security s " +
            "LEFT JOIN s.type t " +
            "LEFT JOIN UserSecurityRel r ON r.security.id = s.id " +
            "WHERE (:typeId IS null or t.id = :typeId)" +
            "AND (:userId IS null OR r.user.id = :userId) " +
            "AND (:name IS null OR s.name LIKE %:name%) " +
            "AND (:shortname IS null OR s.shortname LIKE %:shortname%) " +
            "AND (:secId IS null OR s.secId LIKE %:secId%) " +
            "AND (:primaryBoardId IS null OR s.primaryBoardId LIKE %:primaryBoardId%) " +
            "AND (:typeId IS null OR s.type.id = :typeId)"
    )
    Page<Security> findAllWithFilter(
            @Param("name") String name,
            @Param("shortname") String shortname,
            @Param("secId") String secId,
            @Param("primaryBoardId") String primaryBoardId,
            @Param("typeId") Long typeId,
            @Param("userId") Long userId,

            Pageable pageable);
}
