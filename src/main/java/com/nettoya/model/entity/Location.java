package com.nettoya.model.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "locations")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    @Column(nullable = false, precision = 10, scale = 7)
    private Double latitud;

    @Column(nullable = false, precision = 10, scale = 7)
    private Double longitud;

    @Column(length = 100)
    private String ciudad;

    @Column(length = 100)
    private String provincia;
}

