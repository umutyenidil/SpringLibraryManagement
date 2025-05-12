package com.umutyenidil.librarymanagement.bookcopy;

import lombok.Builder;

@Builder
public record BookCopyResponse(

        BookCopy.AcquisitionType acquisitionType,
        BookCopy.Condition condition,
        String barcode
) {
}
