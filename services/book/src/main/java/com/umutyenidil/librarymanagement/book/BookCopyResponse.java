package com.umutyenidil.librarymanagement.book;

import lombok.Builder;

@Builder
public record BookCopyResponse(
        BookCopy.AcquisitionType acquisitionType,
        BookCopy.Status status,
        BookCopy.Condition condition,
        String barcode
) {
}
