package com.nettoya.service.impl;

import com.nettoya.model.dto.request.CleanerRegistrationDTO;
import com.nettoya.model.dto.request.PasswordChangeDTO;
import com.nettoya.model.dto.request.UserUpdateDTO;
import com.nettoya.model.dto.response.UserProfileResponse;
import com.nettoya.exception.BusinessException;
import com.nettoya.exception.ResourceNotFoundException;
import com.nettoya.model.entity.Cleaner;
import com.nettoya.model.entity.User;
import com.nettoya.model.enums.Role;
import com.nettoya.repository.CleanerRepository;
import com.nettoya.repository.UserRepository;
import com.nettoya.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl extends UserService {

    private final UserRepository userRepository;
    private final CleanerRepository cleanerRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional(readOnly = true)
    public UserProfileResponse getCurrentUserProfile() {
        User user = getCurrentUserEntity();
        return mapToProfileResponse(user);
    }

    @Override
    @Transactional
    public UserProfileResponse updateUserProfile(UserUpdateDTO userDto) {
        User user = getCurrentUserEntity();
        user.setNombre(userDto.getNombre());
        user.setApellido(userDto.getApellido());
        user.setEdad(userDto.getEdad());
        user.setDireccion(userDto.getDireccion());
        user.setFotoUrl(userDto.getFotoUrl());
        user.setDescripcion(userDto.getDescripcion());
        return mapToProfileResponse(userRepository.save(user));
    }

    @Override
    @Transactional
    public void upgradeToCleaner(CleanerRegistrationDTO cleanerDto) {
        User user = getCurrentUserEntity();
        if (user.getRol() == Role.LIMPIADOR) {
            throw new BusinessException("El usuario ya es limpiador");
        }
        
        Cleaner cleaner = Cleaner.builder()
            .user(user)
            .precioHora(cleanerDto.getPrecioHora())
            .disponibilidad(cleanerDto.getDisponibilidad())
            .build();
            
        user.setRol(Role.LIMPIADOR);
        userRepository.save(user);
        cleanerRepository.save(cleaner);
    }

    public User getCurrentUserEntity() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByEmail(email)
            .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));
    }

    private UserProfileResponse mapToProfileResponse(User user) {
        return UserProfileResponse.builder()
            .id(user.getId())
            .email(user.getEmail())
            .nombre(user.getNombre())
            .apellido(user.getApellido())
            .edad(user.getEdad())
            .direccion(user.getDireccion())
            .fotoUrl(user.getFotoUrl())
            .descripcion(user.getDescripcion())
            .rol(user.getRol().name())
            .rating(user.getRating())
            .build();
    }
}

