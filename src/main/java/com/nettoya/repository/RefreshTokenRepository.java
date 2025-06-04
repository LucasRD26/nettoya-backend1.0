package com.nettoya.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nettoya.model.entity.RefreshToken;
import com.nettoya.model.entity.User;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByToken(String token);
    void deleteByUser(User user);
    void deleteByToken(String token);
    boolean existsByToken(String token); // <- Asegúrate de tener este método
}

