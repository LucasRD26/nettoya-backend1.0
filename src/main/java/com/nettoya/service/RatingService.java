package com.nettoya.service;

import com.nettoya.model.dto.request.RatingRequest;
import com.nettoya.model.dto.response.RatingResponse;
import com.nettoya.model.dto.response.RatingSummaryResponse;
import com.nettoya.model.entity.Cleaner;
import com.nettoya.model.entity.Rating;
import com.nettoya.model.entity.User;
import com.nettoya.repository.CleanerRepository;
import com.nettoya.repository.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RatingService {

    @Autowired
    private RatingRepository ratingRepository;
    @Autowired
    private CleanerRepository cleanerRepository;
    @Autowired
    private UserService userService;

    public RatingResponse rateCleaner(Long cleanerId, RatingRequest request) {
        User usuario = userService.getCurrentUser();
        Cleaner cleaner = cleanerRepository.findById(cleanerId)
                .orElseThrow(() -> new RuntimeException("Limpiador no encontrado"));
        Rating rating = new Rating();
        rating.setUsuario(usuario);
        rating.setCleaner(cleaner);
        rating.setValor(request.getValor());
        rating.setComentario(request.getComentario());
        rating.setFecha(LocalDateTime.now());
        ratingRepository.save(rating);
        return mapToRatingResponse(rating);
    }

    public RatingSummaryResponse getCleanerRatings(Long cleanerId) {
        Cleaner cleaner = cleanerRepository.findById(cleanerId)
                .orElseThrow(() -> new RuntimeException("Limpiador no encontrado"));
        Double promedio = ratingRepository.findAverageRatingByCleanerId(cleanerId);
        int totalRatings = ratingRepository.countByCleaner(cleaner);
        RatingSummaryResponse dto = new RatingSummaryResponse();
        dto.setCleanerId(cleanerId);
        dto.setPromedio(promedio != null ? promedio : 0.0);
        dto.setTotalRatings(totalRatings);
        return dto;
    }

    private RatingResponse mapToRatingResponse(Rating rating) {
        RatingResponse dto = new RatingResponse();
        dto.setId(rating.getId());
        dto.setUserId(rating.getUsuario().getId());
        dto.setUsuarioNombre(rating.getUsuario().getNombre());
        dto.setValor(rating.getValor());
        dto.setComentario(rating.getComentario());
        dto.setFecha(rating.getFecha());
        return dto;
    }
}

