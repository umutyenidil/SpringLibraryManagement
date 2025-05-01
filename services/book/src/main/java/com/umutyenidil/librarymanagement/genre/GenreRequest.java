package com.umutyenidil.librarymanagement.genre;

import jakarta.validation.constraints.NotNull;

public record GenreRequest(
        @NotNull(message = "{error.genre.validation.name}")
        String name
) {
}
