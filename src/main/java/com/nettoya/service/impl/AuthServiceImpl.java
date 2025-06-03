package com.nettoya.service.impl;

import com.nettoya.exception.BusinessException;
import com.nettoya.model.dto.request.LoginRequest;
import com.nettoya.model.dto.request.UserRegistrationDTO;
import com.nettoya.model.dto.response.JwtResponse;
import com.nettoya.model.entity.RefreshToken;
import com.nettoya.model.entity.User;
import com.nettoya.model.enums.Role;
import com.nettoya.repository.RefreshTokenRepository;
import com.nettoya.repository.UserRepository;
import com.nettoya.security.JwtUtils;
import com.nettoya.security.UserDetailsImpl;
import com.nettoya.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserDetailsService userDetailsService;
    private final RefreshTokenRepository refreshTokenRepository;    

    @Override
    @Transactional
    public JwtResponse authenticateUser(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        String accessToken = jwtUtils.generateToken(userDetails);
        String refreshToken = jwtUtils.generateRefreshToken(userDetails);

        return new JwtResponse(
                accessToken,
                refreshToken,
                "Bearer",
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getAuthorities()
        );
    }

    @Override
    @Transactional
    public void registerUser(UserRegistrationDTO userDto) {
        if (userRepository.existsByEmail(userDto.getEmail())) {
            throw new RuntimeException("Email ya registrado");
        }
        User user = new User();
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setNombre(userDto.getNombre());
        user.setApellido(userDto.getApellido());
        user.setRol(Role.CLIENTE);
        userRepository.save(user);
    }

    @Override
    @Transactional
    public JwtResponse refreshToken(String refreshToken) {
        if (!jwtUtils.validateToken(refreshToken)) {
            throw new RuntimeException("Refresh token inválido o expirado");
        }
        String username = jwtUtils.getUsernameFromToken(refreshToken);
        UserDetailsImpl userDetails = (UserDetailsImpl) userDetailsService.loadUserByUsername(username);

        String newAccessToken = jwtUtils.generateToken(userDetails);
        String newRefreshToken = jwtUtils.generateRefreshToken(userDetails);

        return new JwtResponse(
                newAccessToken,
                newRefreshToken,
                "Bearer",
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getAuthorities()
        );
    }

    @Override
    @Transactional
    public void logout(String refreshToken) {
        // Buscar el token en la base de datos
        RefreshToken token = refreshTokenRepository.findByToken(refreshToken)
            .orElseThrow(() -> new BusinessException("Refresh token no encontrado"));

        // Eliminar el token
        refreshTokenRepository.delete(token);
        
        // Opcional: Eliminar todos los tokens del usuario
        // refreshTokenRepository.deleteByUser(token.getUser());
    }
}

