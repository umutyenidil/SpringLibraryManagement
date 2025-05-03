package com.umutyenidil.librarymanagement.book;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Builder;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Builder
public record BookCreateRequest(
        @Nullable
        UUID originalBookId,
        @NotNull(message = "{error.book.validation.isbn}")
        String isbn,
        @NotNull(message = "{error.book.validation.name}")
        String name,
        @Nullable
        String description,
        @NotNull(message = "{error.book.validation.audience.notnull}")
        Book.Audience audience,
        @NotNull(message = "{error.book.validation.languageId}")
        UUID languageId,
        @Positive(message = "{error.book.validation.numberOfPages}")
        int numberOfPages,
        @NotNull(message = "{error.book.validation.publisherId}")
        UUID publisherId,
        @NotNull(message = "{error.book.validation.format}")
        Book.Format format,
        @Positive(message = "{error.book.validation.edition}")
        int edition,
        @NotNull(message = "{error.book.validation.publishDate}")
        LocalDate publishDate,
        @Nullable
        List<UUID> authorIds,
        @Nullable
        List<UUID> translatorIds,
        @NotNull(message = "{error.book.validation.genreIds.notnull}")
        @Size(min = 1, message = "{error.book.validation.genreIds.size}")
        List<UUID> genreIds,
        @NotNull(message = "{error.book.validation.categoryIds.notnull}")
        @Size(min = 1, message = "{error.book.validation.categoryIds.size}")
        List<UUID> categoryIds
) {
}
