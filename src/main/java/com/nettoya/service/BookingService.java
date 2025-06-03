package com.nettoya.service;

import com.nettoya.model.dto.request.BookingRequest;
import com.nettoya.model.dto.request.BookingUpdateDTO;
import com.nettoya.model.dto.response.BookingResponse;
import com.nettoya.model.entity.Booking;
import com.nettoya.model.entity.Cleaner;
import com.nettoya.model.entity.User;
import com.nettoya.model.enums.BookingStatus;
import com.nettoya.repository.BookingRepository;
import com.nettoya.repository.CleanerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private CleanerRepository cleanerRepository;
    @Autowired
    private UserService userService;

    public BookingResponse createBooking(BookingRequest request) {
        User cliente = userService.getCurrentUser();
        Cleaner cleaner = cleanerRepository.findById(request.getCleanerId())
                .orElseThrow(() -> new RuntimeException("Limpiador no encontrado"));
        Booking booking = new Booking();
        booking.setCliente(cliente);
        booking.setCleaner(cleaner);
        booking.setFecha(request.getFecha());
        booking.setDireccion(request.getDireccion());
        booking.setEstado(BookingStatus.PENDIENTE);
        bookingRepository.save(booking);
        return mapToBookingResponse(booking);
    }

    public BookingResponse getBookingDetails(Long id) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reserva no encontrada"));
        return mapToBookingResponse(booking);
    }

    public BookingResponse updateBooking(Long id, BookingUpdateDTO updateDto) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reserva no encontrada"));
        booking.setFecha(updateDto.getFecha());
        booking.setDireccion(updateDto.getDireccion());
        bookingRepository.save(booking);
        return mapToBookingResponse(booking);
    }

    public void cancelBooking(Long id) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reserva no encontrada"));
        booking.setEstado(BookingStatus.CANCELADA);
        bookingRepository.save(booking);
    }

    public List<BookingResponse> getMyBookings() {
        User cliente = userService.getCurrentUser();
        return bookingRepository.findByCliente(cliente).stream()
                .map(this::mapToBookingResponse)
                .collect(Collectors.toList());
    }

    private BookingResponse mapToBookingResponse(Booking booking) {
        BookingResponse dto = new BookingResponse();
        dto.setId(booking.getId());
        dto.setClienteId(booking.getCliente().getId());
        dto.setCleanerId(booking.getCleaner().getId());
        dto.setCleanerNombre(booking.getCleaner().getUser().getNombre());
        dto.setCleanerApellido(booking.getCleaner().getUser().getApellido());
        dto.setFecha(booking.getFecha());
        dto.setDireccion(booking.getDireccion());
        dto.setEstado(booking.getEstado().name());
        dto.setCreatedAt(booking.getCreatedAt());
        return dto;
    }
}
