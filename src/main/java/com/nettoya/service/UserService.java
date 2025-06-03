package com.nettoya.service;

import com.nettoya.model.dto.request.UserUpdateDTO;
import com.nettoya.model.dto.request.CleanerRegistrationDTO;
import com.nettoya.model.dto.request.PasswordChangeDTO;
import com.nettoya.model.dto.response.UserProfileResponse;
import com.nettoya.model.entity.Cleaner;
import com.nettoya.model.entity.User;
import com.nettoya.model.enums.Role;
import com.nettoya.repository.CleanerRepository;
import com.nettoya.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CleanerRepository cleanerRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public void changePassword(PasswordChangeDTO passwordDto) {
        User user = getCurrentUser();
        
        // Verificar que la contraseña actual coincida
        if (!passwordEncoder.matches(passwordDto.getOldPassword(), user.getPassword())) {
            throw new RuntimeException("La contraseña actual es incorrecta");
        }
        
        // Validar que la nueva contraseña no esté vacía
        if (passwordDto.getNewPassword() == null || passwordDto.getNewPassword().isBlank()) {
            throw new RuntimeException("La nueva contraseña no puede estar vacía");
        }
        
        // Encriptar y guardar nueva contraseña
        user.setPassword(passwordEncoder.encode(passwordDto.getNewPassword()));
        userRepository.save(user);
    }

    public UserProfileResponse getCurrentUserProfile() {
        User user = getCurrentUser();
        return mapToProfileResponse(user);
    }
    public User getCurrentUserEntity() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    }
    public UserProfileResponse updateUserProfile(UserUpdateDTO userDto) {
        User user = getCurrentUser();
        user.setNombre(userDto.getNombre());
        user.setApellido(userDto.getApellido());
        user.setEdad(userDto.getEdad());
        user.setDireccion(userDto.getDireccion());
        user.setFotoUrl(userDto.getFotoUrl());
        user.setDescripcion(userDto.getDescripcion());
        userRepository.save(user);
        return mapToProfileResponse(user);
    }

    public void upgradeToCleaner(CleanerRegistrationDTO cleanerDto) {
        User user = getCurrentUser();
        Cleaner cleaner = new Cleaner();
        cleaner.setUser(user);
        cleaner.setPrecioHora(cleanerDto.getPrecioHora());
        cleaner.setDisponibilidad(cleanerDto.getDisponibilidad());
        user.setRol(Role.LIMPIADOR);
        userRepository.save(user);
        cleanerRepository.save(cleaner);
    }

    public User getCurrentUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    }

    private UserProfileResponse mapToProfileResponse(User user) {
        UserProfileResponse dto = new UserProfileResponse();
        dto.setId(user.getId());
        dto.setEmail(user.getEmail());
        dto.setNombre(user.getNombre());
        dto.setApellido(user.getApellido());
        dto.setEdad(user.getEdad());
        dto.setDireccion(user.getDireccion());
        dto.setFotoUrl(user.getFotoUrl());
        dto.setDescripcion(user.getDescripcion());
        dto.setRol(user.getRol().name());
        dto.setRating(user.getRating());
        return dto;
    }
}


