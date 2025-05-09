package com.umutyenidil.librarymanagement.auth;

import lombok.Builder;

import java.util.UUID;

@Builder
public record AuthResponse(
        UUID id,
        Auth.Role role
) {
}
