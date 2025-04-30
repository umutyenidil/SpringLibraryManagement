package com.umutyenidil.librarymanagement.language;

import lombok.*;

import java.util.UUID;

@Builder
public record LanguageResponse(
        UUID id,
        String name
) {
}
