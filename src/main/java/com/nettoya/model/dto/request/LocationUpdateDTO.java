package com.nettoya.model.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class LocationUpdateDTO {
    @NotNull
    private Double latitud;

    @NotNull
    private Double longitud;

    private String ciudad;
    private String provincia;
}

