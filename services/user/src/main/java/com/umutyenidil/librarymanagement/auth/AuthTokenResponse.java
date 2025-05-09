package com.umutyenidil.librarymanagement.auth;

import lombok.Builder;

@Builder
public record AuthTokenResponse(
        String accessToken
) {
}
