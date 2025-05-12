package com.umutyenidil.librarymanagement.bookcopy;

import lombok.Builder;

import java.util.UUID;

@Builder
public record BookCopyAvailabilityEvent(
        String barcode,
        boolean available
) {
}