package com.umutyenidil.librarymanagement.author;

import lombok.Builder;

import java.util.UUID;

@Builder
public record AuthorResponse(
        UUID id,
        String name,
        String surname
) {
}
