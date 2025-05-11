package com.umutyenidil.librarymanagement.user;

import lombok.Builder;

import java.util.UUID;

@Builder
public record UserResponse(

        UUID id,
        String name,
        String surname,
        String phone,
        String fullAddress
) {
}
