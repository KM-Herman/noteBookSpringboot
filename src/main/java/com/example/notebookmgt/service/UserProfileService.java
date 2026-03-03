package com.example.notebookmgt.service;

import com.example.notebookmgt.models.UserProfile;
import com.example.notebookmgt.repository.UserProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class UserProfileService {

    private final UserProfileRepository userProfileRepository;

    @Autowired
    public UserProfileService(UserProfileRepository userProfileRepository) {
        this.userProfileRepository = userProfileRepository;
    }

    public List<UserProfile> findAll() {
        return userProfileRepository.findAll();
    }

    public Optional<UserProfile> findById(Long id) {
        return userProfileRepository.findById(id);
    }

    public UserProfile save(UserProfile userProfile) {
        return userProfileRepository.save(userProfile);
    }

    public void deleteById(Long id) {
        userProfileRepository.deleteById(id);
    }
}
