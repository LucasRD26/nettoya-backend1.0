package com.nettoya.model.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

import com.nettoya.model.enums.BookingStatus;

@Entity
@Table(name = "bookings")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    private User cliente;

    @ManyToOne
    @JoinColumn(name = "cleaner_id", nullable = false)
    private Cleaner cleaner;

    @Column(nullable = false)
    private LocalDateTime fecha;

    @Column(length = 255)
    private String direccion;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BookingStatus estado; // PENDIENTE, CONFIRMADA, CANCELADA, COMPLETADA

    @Column(nullable = true)
    private LocalDateTime createdAt;
}

