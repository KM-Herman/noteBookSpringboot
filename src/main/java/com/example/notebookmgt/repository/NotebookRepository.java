package com.example.notebookmgt.repository;

import com.example.notebookmgt.models.Notebook;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotebookRepository extends JpaRepository<Notebook, Long> {
    Page<Notebook> findAll(Pageable pageable);
}
