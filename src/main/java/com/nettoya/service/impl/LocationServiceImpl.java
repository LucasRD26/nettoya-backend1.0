package com.nettoya.service.impl;

import com.nettoya.model.dto.request.LocationUpdateDTO;
import com.nettoya.model.dto.response.CleanerLocationResponse;
import com.nettoya.exception.ResourceNotFoundException;
import com.nettoya.model.entity.Cleaner;
import com.nettoya.model.entity.Location;
import com.nettoya.model.entity.User;
import com.nettoya.repository.LocationRepository;
import com.nettoya.service.LocationService;
import com.nettoya.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LocationServiceImpl extends LocationService {

    private final LocationRepository locationRepository;
    private final UserService userService;

    @Override
    @Transactional
    public void updateUserLocation(LocationUpdateDTO locationDto) {
        User user = userService.getCurrentUserEntity();
        Location location = locationRepository.findByUser(user)
            .orElse(new Location());
            
        location.setLatitud(locationDto.getLatitud());
        location.setLongitud(locationDto.getLongitud());
        location.setCiudad(locationDto.getCiudad());
        location.setProvincia(locationDto.getProvincia());
        location.setUser(user);
        
        locationRepository.save(location);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CleanerLocationResponse> getCleanerLocations() {
        return locationRepository.findAllCleanerLocations().stream()
            .map(this::mapToResponse)
            .collect(Collectors.toList());
    }

    private CleanerLocationResponse mapToResponse(Location location) {
        return CleanerLocationResponse.builder()
            .cleanerId(location.getUser().getId())
            .nombre(location.getUser().getNombre())
            .apellido(location.getUser().getApellido())
            .latitud(location.getLatitud())
            .longitud(location.getLongitud())
            .fotoUrl(location.getUser().getFotoUrl())
            .rating(location.getUser().getRating())
            .build();
    }
}

