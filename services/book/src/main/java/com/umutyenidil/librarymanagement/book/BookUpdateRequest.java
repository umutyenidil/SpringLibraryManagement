package com.umutyenidil.librarymanagement.book;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public record BookUpdateRequest(

        String isbn,
        String name,
        String description,
        Book.Audience audience,
        UUID languageId,
        Integer numberOfPages,
        UUID publisherId,
        Book.Format format,
        Integer edition,
        LocalDate publishDate,
        List<UUID> genreIds,
        List<UUID> categoryIds,
        List<UUID> authorIds,
        List<UUID> translatorIds,
        UUID originalBookId
) {
}
