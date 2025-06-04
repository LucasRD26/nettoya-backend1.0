package com.nettoya.controller;

import com.nettoya.model.dto.request.UserUpdateDTO;
import com.nettoya.model.dto.request.CleanerRegistrationDTO;
import com.nettoya.model.dto.request.PasswordChangeDTO;
import com.nettoya.model.dto.response.UserProfileResponse;
import com.nettoya.security.UserDetailsImpl;
import com.nettoya.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/me")
    public ResponseEntity<UserProfileResponse> getMyProfile() {
        return ResponseEntity.ok(userService.getCurrentUserProfile());
    }

    @PutMapping("/me")
    public ResponseEntity<UserProfileResponse> updateProfile(@Valid @RequestBody UserUpdateDTO userDto) {
        return ResponseEntity.ok(userService.updateUserProfile(userDto));
    }

    @PatchMapping("/me/role")
    public ResponseEntity<?> changeToCleaner(@Valid @RequestBody CleanerRegistrationDTO cleanerDto) {
        userService.upgradeToCleaner(cleanerDto);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/me/password")
    public ResponseEntity<?> changePassword(@Valid @RequestBody PasswordChangeDTO passwordDto) {
        userService.changePassword(passwordDto);
        return ResponseEntity.ok().build();
    }
    @PutMapping("/become-cleaner")
    public ResponseEntity<UserProfileResponse> becomeCleaner(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        UserProfileResponse updatedProfile = userService.becomeCleaner(userDetails.getId());
        return ResponseEntity.ok(updatedProfile);
    }
}

