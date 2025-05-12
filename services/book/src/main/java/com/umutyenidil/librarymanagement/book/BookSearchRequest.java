package com.umutyenidil.librarymanagement.book;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public record BookSearchRequest(
        String q,
        UUID languageId,
        UUID publisherId,
        List<UUID> genreIds,
        List<UUID> categoryIds,
        List<UUID> authorIds,
        Book.Audience audience,
        Book.Format format,
        LocalDate publishDateFrom,
        LocalDate publishDateTo
) {}
