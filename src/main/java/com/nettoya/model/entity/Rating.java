package com.nettoya.model.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "ratings")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Rating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User usuario; // quien califica

    @ManyToOne
    @JoinColumn(name = "cleaner_id", nullable = false)
    private Cleaner cleaner; // a quien califica

    @Column(nullable = false)
    private Integer valor; // 0 a 5

    @Column(length = 500)
    private String comentario;

    @Column(nullable = false)
    private LocalDateTime fecha;
}

