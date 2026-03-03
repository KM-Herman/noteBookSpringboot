package com.example.notebookmgt.controller;

import com.example.notebookmgt.models.Notebook;
import com.example.notebookmgt.service.NotebookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/notebooks")
public class NotebookController {

    private final NotebookService notebookService;

    @Autowired
    public NotebookController(NotebookService notebookService) {
        this.notebookService = notebookService;
    }

    @GetMapping
    public List<Notebook> getAllNotebooks() {
        return notebookService.findAll();
    }

    @GetMapping("/paged")
    public Page<Notebook> getNotebooksPaged(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "title") String sortBy,
            @RequestParam(defaultValue = "asc") String direction) {

        Sort sort = direction.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        return notebookService.findAll(pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Notebook> getNotebookById(@PathVariable Long id) {
        return notebookService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Notebook createNotebook(@RequestBody Notebook notebook) {
        return notebookService.save(notebook);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Notebook> updateNotebook(@PathVariable Long id, @RequestBody Notebook notebookDetails) {
        return notebookService.findById(id)
                .map(notebook -> {
                    notebook.setTitle(notebookDetails.getTitle());
                    notebook.setContent(notebookDetails.getContent());
                    notebook.setCategory(notebookDetails.getCategory());
                    notebook.setLocation(notebookDetails.getLocation());
                    notebook.setTags(notebookDetails.getTags());
                    return ResponseEntity.ok(notebookService.save(notebook));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNotebook(@PathVariable Long id) {
        if (notebookService.findById(id).isPresent()) {
            notebookService.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
