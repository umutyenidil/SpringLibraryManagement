package com.umutyenidil.librarymanagement.book;

import jakarta.validation.constraints.NotNull;

public record BookCopyCreateRequest(
        @NotNull(message = "{error.bookcopy.validation.acquisitionType.notnull}")
        BookCopy.AcquisitionType acquisitionType,
        @NotNull(message = "{error.bookcopy.validation.condition.notnull}")
        BookCopy.Condition condition,
        @NotNull(message = "{error.bookcopy.validation.status.notnull}")
        BookCopy.Status status
) {
}
