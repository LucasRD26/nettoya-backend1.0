package com.nettoya.controller;

import com.nettoya.model.dto.request.LocationUpdateDTO;
import com.nettoya.model.dto.response.CleanerLocationResponse;
import com.nettoya.service.LocationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/location")
public class LocationController {

    @Autowired
    private LocationService locationService;

    @PutMapping("/me")
    public ResponseEntity<?> updateMyLocation(@Valid @RequestBody LocationUpdateDTO locationDto) {
        locationService.updateUserLocation(locationDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/cleaners")
    public ResponseEntity<List<CleanerLocationResponse>> getCleanerLocations() {
        return ResponseEntity.ok(locationService.getCleanerLocations());
    }
}

