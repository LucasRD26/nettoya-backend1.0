package com.nettoya.service.impl;

import com.nettoya.model.dto.response.CleanerProfileResponse;
import com.nettoya.model.entity.Cleaner;
import com.nettoya.model.entity.Location;
import com.nettoya.model.entity.User;
import com.nettoya.repository.CleanerRepository;
import com.nettoya.repository.LocationRepository;
import com.nettoya.service.CleanerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CleanerServiceImpl extends CleanerService { // Cambiar extends por implements

    private final CleanerRepository cleanerRepository;
    private final LocationRepository locationRepository;


    @Override
    @Transactional(readOnly = true)
    public List<CleanerProfileResponse> getTopRatedCleaners() {
        return cleanerRepository.findTop5ByOrderByUserRatingDesc().stream()
            .map(this::mapToCleanerResponse) // Necesita mapear Cleaner
            .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<CleanerProfileResponse> getNearbyCleaners(double lat, double lon) {
        return locationRepository.findCleanerLocations().stream()
            .map(this::mapToCleanerResponseFromLocation) // Renombrar para claridad
            .collect(Collectors.toList());
    }

    // Mapeo para entidad Cleaner
    private CleanerProfileResponse mapToCleanerResponse(Cleaner cleaner) {
        User user = cleaner.getUser();
        return new CleanerProfileResponse(
            user.getId(),
            user.getNombre(),
            user.getApellido(),
            user.getDescripcion(),
            user.getFotoUrl(),
            user.getRating(),
            cleaner.getPrecioHora(),
            cleaner.getDisponibilidad(),
            user.getDireccion(),
            user.getEdad()
        );
    }

    // Mapeo para entidad Location (renombrado)
    private CleanerProfileResponse mapToCleanerResponseFromLocation(Location location) {
        User user = location.getUser();
        Cleaner cleaner = user.getCleaner();
        return new CleanerProfileResponse(
            user.getId(),
            user.getNombre(),
            user.getApellido(),
            user.getDescripcion(),
            user.getFotoUrl(),
            user.getRating(),
            cleaner.getPrecioHora(),
            cleaner.getDisponibilidad(),
            user.getDireccion(),
            user.getEdad()
        );
    }
}


