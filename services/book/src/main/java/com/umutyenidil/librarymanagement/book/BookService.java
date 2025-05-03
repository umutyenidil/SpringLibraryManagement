package com.umutyenidil.librarymanagement.book;

import com.umutyenidil.librarymanagement.author.AuthorMapper;
import com.umutyenidil.librarymanagement.author.AuthorService;
import com.umutyenidil.librarymanagement.category.CategoryMapper;
import com.umutyenidil.librarymanagement.category.CategoryNotFoundException;
import com.umutyenidil.librarymanagement.category.CategoryService;
import com.umutyenidil.librarymanagement.genre.GenreMapper;
import com.umutyenidil.librarymanagement.genre.GenreNotFoundException;
import com.umutyenidil.librarymanagement.genre.GenreService;
import com.umutyenidil.librarymanagement.language.LanguageMapper;
import com.umutyenidil.librarymanagement.language.LanguageService;
import com.umutyenidil.librarymanagement.publisher.PublisherMapper;
import com.umutyenidil.librarymanagement.publisher.PublisherService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;
    private final LanguageService languageService;
    private final LanguageMapper languageMapper;
    private final PublisherService publisherService;
    private final PublisherMapper publisherMapper;
    private final AuthorService authorService;
    private final AuthorMapper authorMapper;
    private final CategoryService categoryService;
    private final CategoryMapper categoryMapper;
    private final GenreMapper genreMapper;
    private final GenreService genreService;


    public UUID saveBook(BookCreateRequest request) {
        var book = bookMapper.toBook(request);

        if (book.getOriginalBook() != null && book.getOriginalBook().getId() != null) {
            var originalBook = bookRepository.findById(book.getOriginalBook().getId())
                    .orElseThrow(() -> new BookNotFoundException("error.book.originalBook.notfound"));

            book.setOriginalBook(originalBook);
        } else {
            book.setOriginalBook(null);
        }

        if(book.getOriginalBook() == null) {
            if(book.getAuthors().isEmpty()) throw new BookAuthorRequiredException("error.book.author.required");

            var authors = book.getAuthors()
                    .stream()
                    .map(author -> {
                        if (author.getId() == null) {
                            throw new BookAuthorNotFoundException("error.book.author.notfound");
                        }

                        var authorResponse = authorService.findAuthorById(author.getId());
                        return authorMapper.toAuthor(authorResponse);
                    })
                    .toList();

            book.setAuthors(authors);
        } else {
            if(book.getTranslators().isEmpty()) throw new BookTranslatorRequiredException("error.book.translator.required");

            var translators = book.getTranslators()
                    .stream()
                    .map(translator -> {
                        if (translator.getId() == null) throw new BookTranslatorNotFoundException("error.book.translator.notfound");

                        var translatorResponse = authorService.findAuthorById(translator.getId());
                        return authorMapper.toAuthor(translatorResponse);
                    })
                    .toList();

            book.setTranslators(translators);
        }

        if (book.getLanguage() != null && book.getLanguage().getId() != null) {
            var languageResponse = languageService.findLanguageById(book.getLanguage().getId());

            book.setLanguage(languageMapper.toLanguage(languageResponse));
        }

        if(book.getPublisher() != null && book.getPublisher().getId() != null) {
            var publisherResponse = publisherService.findPublisherId(book.getPublisher().getId());

            book.setPublisher(publisherMapper.toPublisher(publisherResponse));
        }

        var categories = book.getCategories()
                .stream()
                .map(category -> {
                    if(category.getId() == null) throw new CategoryNotFoundException();

                    var categoryResponse = categoryService.findCategoryById(category.getId());
                    return categoryMapper.toCategory(categoryResponse);
                })
                .toList();
        book.setCategories(categories);

        var genres = book.getGenres()
                .stream()
                .map(genre -> {
                    if(genre.getId() == null) throw new GenreNotFoundException();

                    var genreResponse = genreService.findGenreById(genre.getId());
                    return genreMapper.toGenre(genreResponse);
                })
                .toList();
        book.setGenres(genres);

        var savedBook = bookRepository.save(book);

        return savedBook.getId();
    }

    public Book findBookById(UUID id) {
        return bookRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new BookNotFoundException("error.book.notfound"));
    }

    public Page<Book> findAllBooks(Pageable pageable) {
        return bookRepository.findAllByDeletedAtIsNull(pageable);
    }
}
