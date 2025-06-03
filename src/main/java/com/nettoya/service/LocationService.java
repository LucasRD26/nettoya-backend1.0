package com.nettoya.service;

import com.nettoya.model.dto.request.LocationUpdateDTO;
import com.nettoya.model.dto.response.CleanerLocationResponse;
import com.nettoya.model.entity.Cleaner;
import com.nettoya.model.entity.Location;
import com.nettoya.model.entity.User;
import com.nettoya.repository.CleanerRepository;
import com.nettoya.repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LocationService {

    @Autowired
    private LocationRepository locationRepository;
    @Autowired
    private CleanerRepository cleanerRepository;
    @Autowired
    private UserService userService;

    public void updateUserLocation(LocationUpdateDTO locationDto) {
        User user = userService.getCurrentUser();
        Location location = locationRepository.findByUser(user)
                .orElse(new Location());
        location.setUser(user);
        location.setLatitud(locationDto.getLatitud());
        location.setLongitud(locationDto.getLongitud());
        location.setCiudad(locationDto.getCiudad());
        location.setProvincia(locationDto.getProvincia());
        locationRepository.save(location);
    }

    public List<CleanerLocationResponse> getCleanerLocations() {
        return cleanerRepository.findAll().stream()
                .filter(c -> c.getUser() != null && c.getUser().getRol().name().equals("LIMPIADOR"))
                .map(c -> {
                    Location loc = c.getUser().getLocation(); // Asegúrate de tener el getter
                    CleanerLocationResponse dto = new CleanerLocationResponse();
                    dto.setCleanerId(c.getId());
                    dto.setNombre(c.getUser().getNombre());
                    dto.setApellido(c.getUser().getApellido());
                    dto.setLatitud(loc != null ? loc.getLatitud() : null);
                    dto.setLongitud(loc != null ? loc.getLongitud() : null);
                    dto.setFotoUrl(c.getUser().getFotoUrl());
                    dto.setRating(c.getUser().getRating());
                    return dto;
                })
                .collect(Collectors.toList());
    }
}

