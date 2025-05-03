package com.umutyenidil.librarymanagement.book;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Validated
public record MultiBookCopyCreateRequest(
        @Size(min = 1, message = "{error.book.copies.size}")
        @NotNull(message = "{error.book.copies.notnull}")
        List<@Valid BookCopyCreateRequest> bookCopies
) {
}
