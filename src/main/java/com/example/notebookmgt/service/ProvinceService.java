package com.example.notebookmgt.service;

import com.example.notebookmgt.models.Province;
import com.example.notebookmgt.repository.ProvinceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ProvinceService {

    private final ProvinceRepository provinceRepository;

    @Autowired
    public ProvinceService(ProvinceRepository provinceRepository) {
        this.provinceRepository = provinceRepository;
    }

    public List<Province> findAll() {
        return provinceRepository.findAll();
    }

    public Optional<Province> findById(Long id) {
        return provinceRepository.findById(id);
    }

    public Province save(Province province) {
        return provinceRepository.save(province);
    }

    public void deleteById(Long id) {
        provinceRepository.deleteById(id);
    }
}
