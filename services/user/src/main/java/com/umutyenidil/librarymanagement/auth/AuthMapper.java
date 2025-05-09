package com.umutyenidil.librarymanagement.auth;

import org.springframework.stereotype.Service;

@Service
public class AuthMapper {

    public AuthResponse toAuthResponse(Auth auth) {
        return AuthResponse.builder()
                .id(auth.getId())
                .role(auth.getRole())
                .build();
    }
}
