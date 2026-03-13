package com.example.notebookmgt.repository;

import com.example.notebookmgt.models.Cell;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface CellRepository extends JpaRepository<Cell, Long> {
    Optional<Cell> findByCode(String code);
}
