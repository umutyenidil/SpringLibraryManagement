package com.umutyenidil.librarymanagement.category;

import lombok.Builder;

import java.util.UUID;

@Builder
public record CategoryResponse(
        UUID id,
        String name
) {
}
