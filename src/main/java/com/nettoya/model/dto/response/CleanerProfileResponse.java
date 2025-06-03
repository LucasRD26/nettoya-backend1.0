package com.nettoya.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @Builder
public class CleanerProfileResponse {
    private Long id;
    private String nombre;
    private String apellido;
    private String descripcion;
    private String fotoUrl;
    private Double rating;
    private Double precioHora;
    private String disponibilidad;
    private String direccion;
    private Integer edad;
    
    public CleanerProfileResponse(
        Long id, 
        String nombre, 
        String apellido, 
        String descripcion, 
        String fotoUrl, 
        Double rating, 
        Double precioHora, 
        String disponibilidad,
        String direccion,
        Integer edad
    ) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.descripcion = descripcion;
        this.fotoUrl = fotoUrl;
        this.rating = rating;
        this.precioHora = precioHora;
        this.disponibilidad = disponibilidad;
        this.direccion = direccion;
        this.edad = edad;
    }
}

