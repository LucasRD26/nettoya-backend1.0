package com.nettoya.controller;

import com.nettoya.model.dto.request.RatingRequest;
import com.nettoya.model.dto.response.RatingResponse;
import com.nettoya.model.dto.response.RatingSummaryResponse;
import com.nettoya.service.RatingService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ratings")
public class RatingController {

    @Autowired
    private RatingService ratingService;

    @PostMapping("/cleaner/{cleanerId}")
    public ResponseEntity<RatingResponse> rateCleaner(
            @PathVariable Long cleanerId,
            @Valid @RequestBody RatingRequest ratingRequest) {
        return ResponseEntity.ok(ratingService.rateCleaner(cleanerId, ratingRequest));
    }

    @GetMapping("/cleaner/{cleanerId}")
    public ResponseEntity<RatingSummaryResponse> getCleanerRatings(@PathVariable Long cleanerId) {
        return ResponseEntity.ok(ratingService.getCleanerRatings(cleanerId));
    }
}

