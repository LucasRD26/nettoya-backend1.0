package com.nettoya.service;

import com.nettoya.model.entity.RefreshToken;
import com.nettoya.model.entity.User;

public interface RefreshTokenService {
    RefreshToken createRefreshToken(Long userId);
    boolean existsByToken(String token);
    RefreshToken findByToken(String token);
    void deleteByToken(String token);
    void deleteByUser(User user);
}
