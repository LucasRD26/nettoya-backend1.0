package com.nettoya.service;

import com.nettoya.model.dto.response.CleanerProfileResponse;
import com.nettoya.model.entity.Cleaner;
import com.nettoya.repository.CleanerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CleanerService {

    @Autowired
    private CleanerRepository cleanerRepository;

    public List<CleanerProfileResponse> getTopRatedCleaners() {
        // Suponiendo que tienes un método en el repo para top cleaners
        return cleanerRepository.findAll().stream()
                .sorted((a, b) -> Double.compare(
                        b.getUser().getRating() != null ? b.getUser().getRating() : 0.0,
                        a.getUser().getRating() != null ? a.getUser().getRating() : 0.0))
                .limit(5)
                .map(this::mapToCleanerResponse)
                .collect(Collectors.toList());
    }

    public List<CleanerProfileResponse> getNearbyCleaners(double lat, double lon) {
        return cleanerRepository.findNearbyCleaners(lat, lon).stream()
                .map(this::mapToCleanerResponse)
                .collect(Collectors.toList());
    }

    public List<CleanerProfileResponse> searchCleaners(String query) {
        return cleanerRepository.searchByNameOrSurname(query).stream()
                .map(this::mapToCleanerResponse)
                .collect(Collectors.toList());
    }

    public CleanerProfileResponse getCleanerProfile(Long id) {
        Cleaner cleaner = cleanerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Limpiador no encontrado"));
        return mapToCleanerResponse(cleaner);
    }

    private CleanerProfileResponse mapToCleanerResponse(Cleaner cleaner) {
        CleanerProfileResponse dto = new CleanerProfileResponse();
        dto.setId(cleaner.getId());
        dto.setNombre(cleaner.getUser().getNombre());
        dto.setApellido(cleaner.getUser().getApellido());
        dto.setDescripcion(cleaner.getUser().getDescripcion());
        dto.setFotoUrl(cleaner.getUser().getFotoUrl());
        dto.setRating(cleaner.getUser().getRating());
        dto.setPrecioHora(cleaner.getPrecioHora());
        dto.setDisponibilidad(cleaner.getDisponibilidad());
        dto.setDireccion(cleaner.getUser().getDireccion());
        dto.setEdad(cleaner.getUser().getEdad());
        return dto;
    }
}

