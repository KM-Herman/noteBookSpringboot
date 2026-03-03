package com.example.notebookmgt.controller;

import com.example.notebookmgt.models.UserProfile;
import com.example.notebookmgt.service.UserProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/user-profiles")
public class UserProfileController {

    private final UserProfileService userProfileService;

    @Autowired
    public UserProfileController(UserProfileService userProfileService) {
        this.userProfileService = userProfileService;
    }

    @GetMapping
    public List<UserProfile> getAllProfiles() {
        return userProfileService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserProfile> getProfileById(@PathVariable Long id) {
        return userProfileService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public UserProfile createProfile(@RequestBody UserProfile profile) {
        return userProfileService.save(profile);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserProfile> updateProfile(@PathVariable Long id, @RequestBody UserProfile profileDetails) {
        return userProfileService.findById(id)
                .map(profile -> {
                    profile.setBio(profileDetails.getBio());
                    profile.setProfilePictureUrl(profileDetails.getProfilePictureUrl());
                    return ResponseEntity.ok(userProfileService.save(profile));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProfile(@PathVariable Long id) {
        if (userProfileService.findById(id).isPresent()) {
            userProfileService.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
