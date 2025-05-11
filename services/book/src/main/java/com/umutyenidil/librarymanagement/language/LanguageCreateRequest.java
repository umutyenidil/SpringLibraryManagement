package com.umutyenidil.librarymanagement.language;

import jakarta.validation.constraints.NotNull;

public record LanguageCreateRequest(
        @NotNull(message = "{error.validation.notnull.name}")
        String name
) {
}
