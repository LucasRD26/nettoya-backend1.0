package com.nettoya.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class CleanerLocationResponse {
    private Long cleanerId;
    private String nombre;
    private String apellido;
    private Double latitud;
    private Double longitud;
    private String fotoUrl;
    private Double rating;
}

