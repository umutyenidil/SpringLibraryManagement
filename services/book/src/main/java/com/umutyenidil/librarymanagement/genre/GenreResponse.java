package com.umutyenidil.librarymanagement.genre;

import lombok.Builder;

import java.util.UUID;

@Builder
public record GenreResponse(
        UUID id,
        String name
) {
}
