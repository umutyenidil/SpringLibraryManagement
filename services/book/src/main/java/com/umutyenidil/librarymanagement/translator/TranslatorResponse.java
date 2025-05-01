package com.umutyenidil.librarymanagement.translator;

import lombok.Builder;

import java.util.UUID;

@Builder
public record TranslatorResponse(
        UUID id,
        String name,
        String surname
) {
}
