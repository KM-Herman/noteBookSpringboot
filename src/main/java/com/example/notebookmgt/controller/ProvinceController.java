package com.example.notebookmgt.controller;

import com.example.notebookmgt.models.Province;
import com.example.notebookmgt.service.ProvinceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/provinces")
public class ProvinceController {

    private final ProvinceService provinceService;

    @Autowired
    public ProvinceController(ProvinceService provinceService) {
        this.provinceService = provinceService;
    }

    @GetMapping
    public List<Province> getAllProvinces() {
        return provinceService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Province> getProvinceById(@PathVariable Long id) {
        return provinceService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Province createProvince(@RequestBody Province province) {
        return provinceService.save(province);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Province> updateProvince(@PathVariable Long id, @RequestBody Province provinceDetails) {
        return provinceService.findById(id)
                .map(province -> {
                    province.setName(provinceDetails.getName());
                    return ResponseEntity.ok(provinceService.save(province));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProvince(@PathVariable Long id) {
        if (provinceService.findById(id).isPresent()) {
            provinceService.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
