package com.umutyenidil.librarymanagement.book;

import com.umutyenidil.librarymanagement.author.Author;
import com.umutyenidil.librarymanagement.author.AuthorMapper;
import com.umutyenidil.librarymanagement.category.Category;
import com.umutyenidil.librarymanagement.category.CategoryMapper;
import com.umutyenidil.librarymanagement.genre.Genre;
import com.umutyenidil.librarymanagement.genre.GenreMapper;
import com.umutyenidil.librarymanagement.language.Language;
import com.umutyenidil.librarymanagement.language.LanguageMapper;
import com.umutyenidil.librarymanagement.publisher.Publisher;
import com.umutyenidil.librarymanagement.publisher.PublisherMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookMapper {

    private final LanguageMapper languageMapper;
    private final PublisherMapper publisherMapper;
    private final GenreMapper genreMapper;
    private final CategoryMapper categoryMapper;
    private final AuthorMapper authorMapper;
    private final BookCopyMapper bookCopyMapper;

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

    public BookResponse toBookResponse(Book book) {
        return BookResponse.builder()
                .id(book.getId())
                .originalBook(
                        book.getOriginalBook() != null
                                ? toBookResponse(book.getOriginalBook())
                                : null
                )
                .isbn(book.getIsbn())
                .name(book.getName())
                .description(book.getDescription())
                .audience(book.getAudience())
                .language(languageMapper.toLanguageResponse(book.getLanguage()))
                .numberOfPages(book.getNumberOfPages())
                .publisher(publisherMapper.toPublisherResponse(book.getPublisher()))
                .format(book.getFormat())
                .edition(book.getEdition())
                .publishDate(book.getPublishDate())
                .genres(
                        book.getGenres()
                                .stream()
                                .map(genreMapper::toGenreResponse)
                                .toList()
                )
                .categories(
                        book.getCategories()
                                .stream()
                                .map(categoryMapper::toCategoryResponse)
                                .toList()
                )
                .authors(
                        book.getAuthors()
                                .stream()
                                .map(authorMapper::toAuthorResponse)
                                .toList()
                )
                .translators(
                        book.getTranslators()
                                .stream()
                                .map(authorMapper::toAuthorResponse)
                                .toList()
                )
                .copies(
                        book.getCopies()
                                .stream()
                                .map(bookCopyMapper::toBookCopyResponse)
                                .toList()
                )
                .build();
    }
}
