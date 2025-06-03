package com.nettoya.repository;

import com.nettoya.model.entity.Location;
import com.nettoya.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface LocationRepository extends JpaRepository<Location, Long> {
    Optional<Location> findByUser(User user);
    Optional<Location> findByUserId(Long userId);
    
    @Query("SELECT l FROM Location l WHERE l.user.rol = 'LIMPIADOR'")
    List<Location> findAllCleanerLocations();

    @Query("SELECT l FROM Location l WHERE l.user.rol = 'LIMPIADOR'")
    List<Location> findCleanerLocations();

    @Query("SELECT l FROM Location l WHERE " + "ABS(l.latitud - :lat) < 0.1 AND ABS(l.longitud - :lon) < 0.1")
    List<Location> findNearbyCleaners(@Param("lat") double lat, @Param("lon") double lon);
}

