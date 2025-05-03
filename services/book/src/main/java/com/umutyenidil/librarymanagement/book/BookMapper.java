package com.umutyenidil.librarymanagement.book;

import com.umutyenidil.librarymanagement.author.Author;
import com.umutyenidil.librarymanagement.category.Category;
import com.umutyenidil.librarymanagement.genre.Genre;
import com.umutyenidil.librarymanagement.language.Language;
import com.umutyenidil.librarymanagement.publisher.Publisher;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookMapper {

    public Book toBook(BookCreateRequest request) {
        return Book.builder()
                .originalBook(
                        Book.builder()
                                .id(request.originalBookId())
                                .build()
                )
                .isbn(request.isbn())
                .name(request.name())
                .description(request.description())
                .audience(request.audience())
                .language(
                        Language.builder()
                                .id(request.languageId())
                                .build()
                )
                .numberOfPages(request.numberOfPages())
                .publisher(
                        Publisher.builder()
                                .id(request.publisherId())
                                .build()
                )
                .format(request.format())
                .edition(request.edition())
                .publishDate(request.publishDate())
                .authors(
                        Optional.ofNullable(request.authorIds())
                                .orElseGet(List::of)
                                .stream()
                                .map(
                                        authorId -> Author.builder()
                                                .id(authorId)
                                                .build()
                                )
                                .toList()
                )
                .translators(
                        Optional.ofNullable(request.translatorIds())
                                .orElseGet(List::of)
                                .stream()
                                .map(
                                        translatorId -> Author.builder()
                                                .id(translatorId)
                                                .build()
                                )
                                .toList()
                )
                .categories(
                        Optional.ofNullable(request.categoryIds())
                                .orElseGet(List::of)
                                .stream()
                                .map(
                                        categoryId -> Category.builder()
                                                .id(categoryId)
                                                .build()
                                )
                                .toList()
                )
                .genres(
                        Optional.ofNullable(request.genreIds())
                                .orElseGet(List::of)
                                .stream()
                                .map(
                                        genreId -> Genre.builder()
                                                .id(genreId)
                                                .build()
                                )
                                .toList()
                )
                .build();
    }
}
