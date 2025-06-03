package com.nettoya.repository;

import com.nettoya.model.entity.Cleaner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import java.util.Optional;

public interface CleanerRepository extends JpaRepository<Cleaner, Long> {
    Optional<Cleaner> findByUserId(Long userId);

    // Buscar limpiadores por nombre o apellido
    @Query("SELECT c FROM Cleaner c WHERE LOWER(c.user.nombre) LIKE LOWER(CONCAT('%', :query, '%')) OR LOWER(c.user.apellido) LIKE LOWER(CONCAT('%', :query, '%'))")
    List<Cleaner> searchByNameOrSurname(String query);

    // Buscar limpiadores cercanos (ejemplo simple, puedes mejorar con funciones geográficas)
    @Query("SELECT c FROM Cleaner c JOIN c.user u JOIN u.location l WHERE " +
           "ABS(l.latitud - :lat) < 0.1 AND ABS(l.longitud - :lon) < 0.1")
    List<Cleaner> findNearbyCleaners(double lat, double lon);

    @Query("SELECT c FROM Cleaner c ORDER BY c.user.rating DESC LIMIT 5")
    List<Cleaner> findTop5ByOrderByUserRatingDesc();
}

