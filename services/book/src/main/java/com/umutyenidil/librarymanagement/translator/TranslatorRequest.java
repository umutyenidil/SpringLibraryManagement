package com.umutyenidil.librarymanagement.translator;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record TranslatorRequest(

        @NotNull(message = "{error.translator.validation.name}")
        String name,

        @NotNull(message = "{error.translator.validation.surname}")
        String surname
) {
}
