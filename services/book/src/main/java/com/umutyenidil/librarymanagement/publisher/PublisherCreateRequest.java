package com.umutyenidil.librarymanagement.publisher;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record PublisherCreateRequest(
        @NotNull(message = "{error.publisher.validation.name}")
        String name
) {
}
