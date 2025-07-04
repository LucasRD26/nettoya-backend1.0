package com.nettoya.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class UserProfileResponse {
    private Long id;
    private String email;
    private String nombre;
    private String apellido;
    private Integer edad;
    private String direccion;
    private String fotoUrl;
    private String descripcion;
    private String rol;
    private Double rating;
}
