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

    // Custom JPQL version for clarity and performance
    @Query("SELECT DISTINCT u FROM User u JOIN u.notebooks n JOIN n.location l JOIN l.province p WHERE p.name = :name OR p.code = :code")
    List<User> findUsersByProvince(@Param("name") String name, @Param("code") String code);
}
