package com.umutyenidil.librarymanagement.auth;

import lombok.Builder;

@Builder
public record AuthResponse(
        String accessToken
) {
}
