package com.example.notebookmgt.repository;

import com.example.notebookmgt.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);

    // Method name query derivation
    List<User> findDistinctByNotebooksLocationProvinceNameOrNotebooksLocationProvinceCode(String name, String code);

    // Custom JPQL version for the new hierarchy
    @Query("SELECT DISTINCT u FROM User u " +
           "JOIN u.village v " +
           "JOIN v.cell c " +
           "JOIN c.sector s " +
           "JOIN s.district d " +
           "JOIN d.province p " +
           "WHERE p.name = :name OR p.code = :code")
    List<User> findUsersByProvince(@Param("name") String name, @Param("code") String code);
}
