package com.example.notebookmgt.controller;

import com.example.notebookmgt.service.AdministrativeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
public class AdministrativeController {

    private final AdministrativeService administrativeService;

    @Autowired
    public AdministrativeController(AdministrativeService administrativeService) {
        this.administrativeService = administrativeService;
    }

    @PostMapping("/import-locations")
    public ResponseEntity<String> importLocations(@RequestBody String json) {
        try {
            administrativeService.importAdministrativeData(json);
            return ResponseEntity.ok("Administrative data imported successfully");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Failed to import data: " + e.getMessage());
        }
    }
}
