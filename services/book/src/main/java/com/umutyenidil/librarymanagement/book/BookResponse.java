package com.umutyenidil.librarymanagement.book;

import com.umutyenidil.librarymanagement.author.AuthorResponse;
import com.umutyenidil.librarymanagement.category.CategoryResponse;
import com.umutyenidil.librarymanagement.genre.GenreResponse;
import com.umutyenidil.librarymanagement.language.LanguageResponse;
import com.umutyenidil.librarymanagement.publisher.PublisherResponse;
import lombok.Builder;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Builder
public record BookResponse(
        UUID id,
        BookResponse originalBook,
        String isbn,
        String name,
        String description,
        Book.Audience audience,
        LanguageResponse language,
        int numberOfPages,
        PublisherResponse publisher,
        Book.Format format,
        int edition,
        LocalDate publishDate,
        List<GenreResponse> genres,
        List<CategoryResponse> categories,
        List<AuthorResponse> authors,
        List<AuthorResponse> translators
) {
}
