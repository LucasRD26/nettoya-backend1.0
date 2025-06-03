package com.nettoya.service;

import com.nettoya.model.dto.request.LoginRequest;
import com.nettoya.model.dto.request.UserRegistrationDTO;
import com.nettoya.model.dto.response.JwtResponse;

public interface AuthService {
    JwtResponse authenticateUser(LoginRequest request);
    void registerUser(UserRegistrationDTO userDto);
    JwtResponse refreshToken(String refreshToken);
    void logout(String refreshToken);
}



