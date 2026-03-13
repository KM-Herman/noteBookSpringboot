package com.example.notebookmgt.service;

import com.example.notebookmgt.models.User;
import com.example.notebookmgt.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final AdministrativeService administrativeService;

    @Autowired
    public UserService(UserRepository userRepository, AdministrativeService administrativeService) {
        this.userRepository = userRepository;
        this.administrativeService = administrativeService;
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    public User createUser(User user, String villageIdentity) {
        administrativeService.findVillageByNameOrCode(villageIdentity)
                .ifPresent(user::setVillage);
        return userRepository.save(user);
    }

    public List<User> getUsersByProvince(String province) {
        // Try searching by name or code
        return userRepository.findUsersByProvince(province, province);
    }

    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }
}
