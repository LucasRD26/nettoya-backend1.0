package com.nettoya.service.impl;

import com.nettoya.model.dto.request.BookingRequest;
import com.nettoya.model.dto.response.BookingResponse;
import com.nettoya.exception.BusinessException;
import com.nettoya.exception.ResourceNotFoundException;
import com.nettoya.model.entity.Booking;
import com.nettoya.model.entity.Cleaner;
import com.nettoya.model.entity.User;
import com.nettoya.model.enums.BookingStatus;
import com.nettoya.repository.BookingRepository;
import com.nettoya.repository.CleanerRepository;
import com.nettoya.service.BookingService;
import com.nettoya.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl extends BookingService {

    private final BookingRepository bookingRepository;
    private final CleanerRepository cleanerRepository;
    private final UserService userService;

    @Override
    @Transactional
    public BookingResponse createBooking(BookingRequest request) {
        User cliente = userService.getCurrentUserEntity();
        Cleaner cleaner = cleanerRepository.findById(request.getCleanerId())
            .orElseThrow(() -> new ResourceNotFoundException("Limpiador no encontrado"));
        
        validateAvailability(cleaner, request.getFecha());
        
        Booking booking = Booking.builder()
            .cliente(cliente)
            .cleaner(cleaner)
            .fecha(request.getFecha())
            .direccion(request.getDireccion())
            .estado(BookingStatus.PENDIENTE)
            .build();
            
        return mapToResponse(bookingRepository.save(booking));
    }

    private void validateAvailability(Cleaner cleaner, LocalDateTime fecha) {
        if (bookingRepository.existsByCleanerAndFecha(cleaner, fecha)) {
            throw new BusinessException("El limpiador no está disponible en esa fecha");
        }
    }

    private BookingResponse mapToResponse(Booking booking) {
        return BookingResponse.builder()
            .id(booking.getId())
            .clienteId(booking.getCliente().getId())
            .cleanerId(booking.getCleaner().getId())
            .cleanerNombre(booking.getCleaner().getUser().getNombre())
            .cleanerApellido(booking.getCleaner().getUser().getApellido())
            .fecha(booking.getFecha())
            .direccion(booking.getDireccion())
            .estado(booking.getEstado().name())
            .createdAt(booking.getCreatedAt())
            .build();
    }
}

