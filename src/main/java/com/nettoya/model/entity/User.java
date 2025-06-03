package com.nettoya.model.entity;

import com.nettoya.model.enums.Role;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "users")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, length = 100)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, length = 50)
    private String nombre;

    @Column(nullable = false, length = 50)
    private String apellido;

    private Integer edad;

    @Column(length = 255)
    private String direccion;

    @Column(length = 255)
    private String fotoUrl;

    @Column(length = 500)
    private String descripcion;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role rol; // CLIENTE, LIMPIADOR

    @Column(precision = 2, scale = 1)
    private Double rating;

    // Relación bidireccional con Location
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Location location;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Cleaner cleaner;

}



