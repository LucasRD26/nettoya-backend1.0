package com.nettoya.repository;

import com.nettoya.model.entity.Rating;
import com.nettoya.model.entity.Cleaner;
import com.nettoya.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface RatingRepository extends JpaRepository<Rating, Long> {
    List<Rating> findByCleaner(Cleaner cleaner);
    List<Rating> findByUsuario(User usuario);

    @Query("SELECT AVG(r.valor) FROM Rating r WHERE r.cleaner.id = :cleanerId")
    Double findAverageRatingByCleanerId(Long cleanerId);

    int countByCleaner(Cleaner cleaner);
}

