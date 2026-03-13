package com.example.notebookmgt.repository;

import com.example.notebookmgt.models.Village;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface VillageRepository extends JpaRepository<Village, Long> {
    Optional<Village> findByCode(String code);
    Optional<Village> findByName(String name);
}
