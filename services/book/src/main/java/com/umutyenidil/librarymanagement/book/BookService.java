package com.umutyenidil.librarymanagement.book;

import com.umutyenidil.librarymanagement.author.Author;
import com.umutyenidil.librarymanagement.author.AuthorMapper;
import com.umutyenidil.librarymanagement.author.AuthorRepository;
import com.umutyenidil.librarymanagement.author.AuthorService;
import com.umutyenidil.librarymanagement.category.Category;
import com.umutyenidil.librarymanagement.category.CategoryMapper;
import com.umutyenidil.librarymanagement.category.CategoryRepository;
import com.umutyenidil.librarymanagement.category.CategoryService;
import com.umutyenidil.librarymanagement.common.exception.ResourceNotFoundException;
import com.umutyenidil.librarymanagement.common.exception.ValidationFieldException;
import com.umutyenidil.librarymanagement.genre.Genre;
import com.umutyenidil.librarymanagement.genre.GenreMapper;
import com.umutyenidil.librarymanagement.genre.GenreRepository;
import com.umutyenidil.librarymanagement.genre.GenreService;
import com.umutyenidil.librarymanagement.language.Language;
import com.umutyenidil.librarymanagement.language.LanguageMapper;
import com.umutyenidil.librarymanagement.language.LanguageRepository;
import com.umutyenidil.librarymanagement.language.LanguageService;
import com.umutyenidil.librarymanagement.publisher.Publisher;
import com.umutyenidil.librarymanagement.publisher.PublisherMapper;
import com.umutyenidil.librarymanagement.publisher.PublisherRepository;
import com.umutyenidil.librarymanagement.publisher.PublisherService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.cglib.core.Local;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
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
    private final LanguageRepository languageRepository;
    private final PublisherRepository publisherRepository;
    private final GenreRepository genreRepository;
    private final CategoryRepository categoryRepository;
    private final AuthorRepository authorRepository;

    public UUID saveBook(BookCreateRequest request) {

        // kitap olusturma istegini kitap nesnesine donustur
        var book = bookMapper.toBook(request);

        // kitap icin orijinal kitap id eklenmis mi kontrol et
        if (book.getOriginalBook() != null && book.getOriginalBook().getId() != null) {

            // kitap icin eklenen orijinal kitap sistemde var mi diye kontrol et yoksa hata firlat
            var originalBook = bookRepository.findById(book.getOriginalBook().getId())
                    .orElseThrow(() -> new ResourceNotFoundException("error.book.originalBook.notfound"));

            // orijinal kitap bulunduysa kitap nesnesine ekle
            book.setOriginalBook(originalBook);
        } else {

            // kitap icin orijinal kitap eklenmemisse orijinal kitabi null olarak isaretle
            book.setOriginalBook(null);
        }

        // orijinal kitap eklenmemisse bu kitap asil kitaptir, yoksa ceviri kitaptir ve yazar disinda cevirmenler gereklidir
        if (book.getOriginalBook() == null) {

            // kitabin yazar listesi bos mu diye kontrol et, bos ise hata firlat
            if (book.getAuthors().isEmpty())
                throw new ValidationFieldException("authorIds", "error.book.author.required");

            // yazar listesinde sadece yazarlarin id'leri var, bunlari kontrol et ve id'lerine gore yazarlari getir
            var authors = book.getAuthors()
                    .stream()
                    .map(author -> {

                        // yazar id bos ise hata firlat
                        if (author.getId() == null) throw new ResourceNotFoundException("error.book.author.notfound");

                        // yazari getir ve donustur
                        var authorResponse = authorService.findAuthorById(author.getId());
                        return authorMapper.toAuthor(authorResponse);
                    })
                    .toList();

            // id'lerinden bulunan yazarlari kitap nesnesine ekle
            book.setAuthors(authors);
        } else {
            if (book.getTranslators().isEmpty())
                throw new ValidationFieldException("translatorIds", "error.book.translator.required");

            var translators = book.getTranslators()
                    .stream()
                    .map(translator -> {
                        if (translator.getId() == null)
                            throw new ResourceNotFoundException("error.book.translator.notfound");

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

        if (book.getPublisher() != null && book.getPublisher().getId() != null) {
            var publisherResponse = publisherService.findPublisherId(book.getPublisher().getId());

            book.setPublisher(publisherMapper.toPublisher(publisherResponse));
        }

        var categories = book.getCategories()
                .stream()
                .map(category -> {
                    if (category.getId() == null) throw new ResourceNotFoundException("error.category.notfound");

                    var categoryResponse = categoryService.findCategoryById(category.getId());
                    return categoryMapper.toCategory(categoryResponse);
                })
                .toList();
        book.setCategories(categories);

        var genres = book.getGenres()
                .stream()
                .map(genre -> {
                    if (genre.getId() == null) throw new ResourceNotFoundException("error.genre.notfound");

                    var genreResponse = genreService.findGenreById(genre.getId());
                    return genreMapper.toGenre(genreResponse);
                })
                .toList();
        book.setGenres(genres);

        var savedBook = bookRepository.save(book);

        return savedBook.getId();
    }

    public Book findBookById(UUID id) {
        return bookRepository.findByIdAndDeletedAtIsNull((id))
                .orElseThrow(() -> new ResourceNotFoundException("error.book.notfound"));
    }

    public Page<Book> findAllBooks(Pageable pageable) {
        return bookRepository.findAllByDeletedAtIsNull((pageable));
    }

    public Page<Book> searchBooks(BookSearchRequest request, Pageable pageable) {

        // gelen search parametrelerine gore bir filtreleme listesi olustur
        Specification<Book> spec = BookSpecification.build(request);

        // olusturulan filtrelemeye gore tum kayitlari page halinde getir
        return bookRepository.findAll(spec, pageable);
    }


    public void deleteBookById(UUID id) {

        // kitap var mi diye kontrol et
        bookRepository.findById(id)
                .ifPresent(book -> {

                    // kitap varsa deleted_at degerini simdi yap ve veritabanina kaydet
                    book.setDeletedAt(LocalDateTime.now());
                    bookRepository.save(book);
                });
    }

    @Transactional
    public void updateBook(UUID id, BookUpdateRequest request) {

        // kitabi getir yoksa hata firlat
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("error.book.notfound"));

        // iliskili olmayan alanlari set et
        if (request.isbn() != null) {
            book.setIsbn(request.isbn());
        }

        if (request.name() != null) {
            book.setName(request.name());
        }

        if (request.description() != null) {
            book.setDescription(request.description());
        }

        if (request.audience() != null) {
            book.setAudience(request.audience());
        }

        if (request.numberOfPages() != null) {
            book.setNumberOfPages(request.numberOfPages());
        }

        if (request.edition() != null) {
            book.setEdition(request.edition());
        }

        if (request.format() != null) {
            book.setFormat(request.format());
        }

        if (request.publishDate() != null) {
            book.setPublishDate(request.publishDate());
        }


        // dili kontrol et, varsa ata yoksa hata firlat
        if (request.languageId() != null) {

            Language language = languageRepository.findById(request.languageId())
                    .orElseThrow(() -> new ValidationFieldException("languageId", "error.language.notfound"));
            book.setLanguage(language);
        }

        // yayin evini kontrol et, varsa ata yoksa hata firlat
        if (request.publisherId() != null) {

            Publisher publisher = publisherRepository.findById(request.publisherId())
                    .orElseThrow(() -> new ValidationFieldException("publisherId", "error.publisher.notfound"));
            book.setPublisher(publisher);
        }

        // orijinal kitabi kontrol et, varsa ata yoksa hata firlat
        if (request.originalBookId() != null) {

            Book original = bookRepository.findById(request.originalBookId())
                    .orElseThrow(() -> new ValidationFieldException("originalBookId","error.book.originalBook.notfound"));
            book.setOriginalBook(original);
        }

        // turleri kontrol et, varsa herhangi birisi ata bulunamazsa hata firlat
        if (request.genreIds() != null) {

            List<Genre> genres = genreRepository.findAllById(request.genreIds());

            if (genres.size() != request.genreIds().size()) throw new ValidationFieldException("genreIds","error.book.validation.genreIds.notfound");

            book.setGenres(genres);
        }

        // kategorileri kontrol et, varsa herhangi birisi ata bulunamazsa hata firlat
        if (request.categoryIds() != null) {

            List<Category> categories = categoryRepository.findAllById(request.categoryIds());

            if (categories.size() != request.categoryIds().size()) throw new ValidationFieldException("categoryIds","error.book.validation.categoryIds.notfound");

            book.setCategories(categories);
        }

        // yazarlari kontrol et, varsa herhangi birisi ata bulunamazsa hata firlat
        if (request.authorIds() != null) {

            List<Author> authors = authorRepository.findAllById(request.authorIds());

            if (authors.size() != request.authorIds().size()) throw new ValidationFieldException("authorIds","error.book.validation.authorIds.notfound");

            book.setAuthors(authors);
        }

        // cevirmenleri kontrol et, varsa herhangi birisi ata bulunamazsa hata firlat
        if (request.translatorIds() != null) {

            List<Author> translators = authorRepository.findAllById(request.translatorIds());

            if (translators.size() != request.translatorIds().size()) throw new ValidationFieldException("translatorIds","error.book.validation.translatorIds.notfound");

            book.setTranslators(translators);
        }
    }

}
