package com.nettoya.model.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CleanerRegistrationDTO {
    @NotNull
    @Positive
    private Double precioHora;

    @NotNull
    private String disponibilidad;
}

