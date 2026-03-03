package com.example.notebookmgt.service;

import com.example.notebookmgt.models.Notebook;
import com.example.notebookmgt.repository.NotebookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class NotebookService {

    private final NotebookRepository notebookRepository;

    @Autowired
    public NotebookService(NotebookRepository notebookRepository) {
        this.notebookRepository = notebookRepository;
    }

    public List<Notebook> findAll() {
        return notebookRepository.findAll();
    }

    public Page<Notebook> findAll(Pageable pageable) {
        return notebookRepository.findAll(pageable);
    }

    public Optional<Notebook> findById(Long id) {
        return notebookRepository.findById(id);
    }

    public Notebook save(Notebook notebook) {
        return notebookRepository.save(notebook);
    }

    public void deleteById(Long id) {
        notebookRepository.deleteById(id);
    }
}
