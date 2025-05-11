package com.umutyenidil.librarymanagement.genre;

import jakarta.validation.constraints.NotNull;

public record GenreCreateRequest(
        @NotNull(message = "{error.genre.validation.name}")
        String name
) {
}
