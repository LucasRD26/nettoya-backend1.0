package com.nettoya.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class BookingResponse {
    private Long id;
    private Long clienteId;
    private Long cleanerId;
    private String cleanerNombre;
    private String cleanerApellido;
    private LocalDateTime fecha;
    private String direccion;
    private String estado;
    private LocalDateTime createdAt;
}

