package com.nettoya.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class RatingResponse {
    private Long id;
    private Long userId;
    private String usuarioNombre;
    private Integer valor;
    private String comentario;
    private LocalDateTime fecha;
}

