package com.umutyenidil.librarymanagement.category;

import jakarta.validation.constraints.NotNull;

public record CategoryRequest(

        @NotNull(message = "{error.category.validation.name}")
        String name
) {
}
