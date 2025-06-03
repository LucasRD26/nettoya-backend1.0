package com.nettoya.model.dto.response;

import java.util.Collection;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class JwtResponse {
    private String accessToken;
    private String refreshToken;
    private String type = "Bearer";
    private Long id;
    private String email;
    private Collection<?> roles;

    public JwtResponse(String accessToken, String refreshToken, String type) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.type = type;
    }
}

