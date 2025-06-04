package com.nettoya.controller;

import com.nettoya.model.dto.response.CleanerProfileResponse;
import com.nettoya.service.CleanerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/cleaners")
public class CleanerController {

    @Autowired
    private CleanerService cleanerService;

    @GetMapping
    public ResponseEntity<List<CleanerProfileResponse>> getAllCleaners() {
        return ResponseEntity.ok(cleanerService.getAllCleaners());
    }
    
    @GetMapping("/top")
    public ResponseEntity<List<CleanerProfileResponse>> getTopCleaners() {
        return ResponseEntity.ok(cleanerService.getTopRatedCleaners());
    }

    @GetMapping("/nearby")
    public ResponseEntity<List<CleanerProfileResponse>> getNearbyCleaners(
            @RequestParam double lat, @RequestParam double lon) {
        return ResponseEntity.ok(cleanerService.getNearbyCleaners(lat, lon));
    }

    @GetMapping("/search")
    public ResponseEntity<List<CleanerProfileResponse>> searchCleaners(@RequestParam String query) {
        return ResponseEntity.ok(cleanerService.searchCleaners(query));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CleanerProfileResponse> getCleanerProfile(@PathVariable Long id) {
        return ResponseEntity.ok(cleanerService.getCleanerProfile(id));
    }
}

