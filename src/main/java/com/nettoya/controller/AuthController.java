package com.nettoya.controller;

import com.nettoya.exception.BusinessException;
import com.nettoya.model.dto.request.LoginRequest;
import com.nettoya.model.dto.request.UserRegistrationDTO;
import com.nettoya.model.dto.request.TokenRefreshRequest;
import com.nettoya.model.dto.response.JwtResponse;
import com.nettoya.model.entity.RefreshToken;
import com.nettoya.security.JwtUtils;
import com.nettoya.security.UserDetailsImpl;
import com.nettoya.service.AuthService;
import com.nettoya.service.RefreshTokenService;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;
    @Autowired
    private RefreshTokenService refreshTokenService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtUtils jwtUtils;

    @PostMapping("/login")
public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest) {
    Authentication authentication = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
    );
    
    SecurityContextHolder.getContext().setAuthentication(authentication);
    UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
    
    // Genera tokens
    String accessToken = jwtUtils.generateToken(userDetails);
    RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDetails.getId());
    
    // Guarda refresh token en la base de datos
    return ResponseEntity.ok()
        .header(HttpHeaders.SET_COOKIE, jwtUtils.generateHttpOnlyJwtCookie(accessToken).toString())
        .body(new JwtResponse(
            accessToken,
            refreshToken.getToken(),
            "Bearer", // tokenType
            userDetails.getId(),
            userDetails.getUsername(),
            userDetails.getEmail(),
            userDetails.getAuthorities()
        ));
}

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody UserRegistrationDTO userDto) {
        authService.registerUser(userDto);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/refresh")
    public ResponseEntity<JwtResponse> refreshToken(@RequestBody TokenRefreshRequest request) {
        return ResponseEntity.ok(authService.refreshToken(request.getRefreshToken()));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestBody TokenRefreshRequest request) {
        String refreshToken = request.getRefreshToken();
        
        if (!refreshTokenService.existsByToken(refreshToken)) {
            throw new BusinessException("Refresh token no encontrado");
        }
        
        refreshTokenService.deleteByToken(refreshToken);
        return ResponseEntity.ok().build();
    }
 
}

