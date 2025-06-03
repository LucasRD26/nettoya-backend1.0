package com.nettoya.model.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "cleaners")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Cleaner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    @Column(nullable = false)
    private Double precioHora;

    @Column(length = 255)
    private String disponibilidad;
}

