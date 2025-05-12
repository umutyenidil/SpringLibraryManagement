package com.umutyenidil.librarymanagement.bookcopy;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;

public record BookCopyCreateRequest(

        @Nullable
        String barcode,

        @NotNull(message = "{error.bookcopy.validation.acquisitionType.notnull}")
        BookCopy.AcquisitionType acquisitionType,

        @NotNull(message = "{error.bookcopy.validation.condition.notnull}")
        BookCopy.Condition condition
) {
}
