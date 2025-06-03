package com.nettoya.repository;

import com.nettoya.model.entity.Booking;
import com.nettoya.model.entity.Cleaner;
import com.nettoya.model.entity.User;
import com.nettoya.model.enums.BookingStatus;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByCliente(User cliente);
    List<Booking> findByCleanerId(Long cleanerId);
    boolean existsByCleanerAndFecha(Cleaner cleaner, LocalDateTime fecha);
    boolean existsByClienteAndCleanerAndEstado(User cliente, Cleaner cleaner, BookingStatus estado);
}

