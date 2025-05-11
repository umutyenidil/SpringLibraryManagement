package com.umutyenidil.librarymanagement.author;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record AuthorCreateRequest(

        @NotNull(message = "{error.author.validation.name}")
        String name,

        @NotNull(message = "{error.author.validation.surname}")
        String surname
) {
}
