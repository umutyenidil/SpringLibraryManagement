package com.umutyenidil.librarymanagement.book;

import com.umutyenidil.librarymanagement.Category;
import com.umutyenidil.librarymanagement._core.entity.SoftDeletableEntity;
import com.umutyenidil.librarymanagement.author.Author;
import com.umutyenidil.librarymanagement.genre.Genre;
import com.umutyenidil.librarymanagement.language.Language;
import com.umutyenidil.librarymanagement.publisher.Publisher;
import com.umutyenidil.librarymanagement.translator.Translator;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
public class Book extends SoftDeletableEntity {
    @ManyToOne
    @JoinColumn(name = "original_book_id")
    private Book originalBook;

    private String name;
    private String description;

    @Enumerated(EnumType.STRING)
    private Audience audience;

    @ManyToOne
    @JoinColumn(name = "language_id", nullable = false)
    private Language language;

    private int numberOfPages;

    @ManyToOne
    @JoinColumn(name = "publisher_id", nullable = false)
    private Publisher publisher;

    @Enumerated(EnumType.STRING)
    private Format format;

    private int edition;

    private LocalDate publishDate;

    @ManyToMany(mappedBy = "books")
    @JoinTable(
            name = "book_genres",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id")
    )
    private List<Genre> genres;

    @ManyToMany(mappedBy = "books")
    @JoinTable(
            name = "book_categories",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private List<Category> categories;

    @ManyToMany(mappedBy = "books")
    @JoinTable(
            name = "book_authors",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id")
    )
    private List<Author> authors;

    @ManyToMany(mappedBy = "books")
    @JoinTable(
            name = "book_translators",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "translator_id")
    )
    private List<Translator> translators;

    @OneToMany(mappedBy = "book")
    private List<BookCopy> copies;

    public enum Audience {
        CHILDREN,
        ADULTS,
        GENERAL
    }

    public enum Format {
        HARDCOVER,
        PAPERBACK
    }
}
