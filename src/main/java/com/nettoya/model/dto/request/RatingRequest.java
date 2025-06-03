package com.nettoya.model.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class RatingRequest {
    @NotNull
    @Min(0)
    @Max(5)
    private Integer valor;

    private String comentario;
}

