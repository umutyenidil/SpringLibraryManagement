package com.umutyenidil.librarymanagement.bookcopy;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;
import java.util.UUID;

public record MultiBookCopyCreateRequest(

        @NotNull(message = "{validation.bookcopy.bookid.notnull}")
        UUID bookId,

        @Size(min = 1, message = "{error.book.copies.size}")
        @NotNull(message = "{error.book.copies.notnull}")
        List<@Valid BookCopyCreateRequest> bookCopies
) {
}
