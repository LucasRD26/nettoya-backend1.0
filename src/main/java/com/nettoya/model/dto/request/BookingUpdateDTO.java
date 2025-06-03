package com.nettoya.model.dto.request;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
public class BookingUpdateDTO {
    @NotNull
    @Future
    private LocalDateTime fecha;

    @NotNull
    private String direccion;
}

