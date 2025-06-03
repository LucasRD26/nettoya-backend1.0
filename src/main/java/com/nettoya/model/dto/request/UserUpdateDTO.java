package com.nettoya.model.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UserUpdateDTO {
    @NotBlank
    private String nombre;

    @NotBlank
    private String apellido;

    private Integer edad;
    private String direccion;
    private String fotoUrl;
    private String descripcion;
}

