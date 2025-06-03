package com.nettoya.service.impl;

import com.nettoya.model.dto.request.RatingRequest;
import com.nettoya.model.dto.response.RatingResponse;
import com.nettoya.model.dto.response.RatingSummaryResponse;
import com.nettoya.exception.BusinessException;
import com.nettoya.exception.ResourceNotFoundException;
import com.nettoya.model.entity.Cleaner;
import com.nettoya.model.entity.Rating;
import com.nettoya.model.entity.User;
import com.nettoya.model.enums.BookingStatus;
import com.nettoya.repository.BookingRepository;
import com.nettoya.repository.CleanerRepository;
import com.nettoya.repository.RatingRepository;
import com.nettoya.service.RatingService;
import com.nettoya.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class RatingServiceImpl extends RatingService {

    private final RatingRepository ratingRepository;
    private final BookingRepository bookingRepository;
    private final CleanerRepository cleanerRepository;
    private final UserService userService;

    @Override
    @Transactional
    public RatingResponse rateCleaner(Long cleanerId, RatingRequest request) {
        User usuario = userService.getCurrentUserEntity();
        Cleaner cleaner = cleanerRepository.findById(cleanerId)
            .orElseThrow(() -> new ResourceNotFoundException("Limpiador no encontrado"));
        
        validateRatingPermission(usuario, cleaner);
        
        Rating rating = Rating.builder()
            .usuario(usuario)
            .cleaner(cleaner)
            .valor(request.getValor())
            .comentario(request.getComentario())
            .fecha(LocalDateTime.now())
            .build();
            
        return mapToResponse(ratingRepository.save(rating));
    }

    private void validateRatingPermission(User usuario, Cleaner cleaner) {
        if (!bookingRepository.existsByClienteAndCleanerAndEstado(
                usuario, cleaner, BookingStatus.COMPLETADA)) {
            throw new BusinessException("Debes tener una reserva completada para calificar");
        }
    }

    private RatingResponse mapToResponse(Rating rating) {
        return RatingResponse.builder()
            .id(rating.getId())
            .userId(rating.getUsuario().getId())
            .usuarioNombre(rating.getUsuario().getNombre())
            .valor(rating.getValor())
            .comentario(rating.getComentario())
            .fecha(rating.getFecha())
            .build();
    }
}

