package com.umutyenidil.librarymanagement.publisher;

import lombok.Builder;

import java.util.UUID;

@Builder
public record PublisherResponse(
        UUID id,
        String name
) {
}
