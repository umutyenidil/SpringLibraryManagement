package com.umutyenidil.librarymanagement.book;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.umutyenidil.librarymanagement.author.Author;
import com.umutyenidil.librarymanagement.category.Category;
import com.umutyenidil.librarymanagement.genre.Genre;
import com.umutyenidil.librarymanagement.language.Language;
import com.umutyenidil.librarymanagement.publisher.Publisher;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "original_book_id")
    private Book originalBook;

    @Column(nullable = false)
    private String isbn;

    @Column(nullable = false)
    private String name;

    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Audience audience;

    @ManyToOne
    @JoinColumn(name = "language_id", nullable = false)
    private Language language;

    @Column(nullable = false)
    private int numberOfPages;

    @ManyToOne
    @JoinColumn(name = "publisher_id", nullable = false)
    private Publisher publisher;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Format format;

    @Column(nullable = false)
    private int edition;

    @Column(nullable = false)
    private LocalDate publishDate;

    @ManyToMany
    @JoinTable(
            name = "book_genres",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id")
    )
    private List<Genre> genres;

    @ManyToMany
    @JoinTable(
            name = "book_categories",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private List<Category> categories;

    @ManyToMany
    @JoinTable(
            name = "book_authors",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id")
    )
    private List<Author> authors;

    @ManyToMany
    @JoinTable(
            name = "book_translators",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "translator_id")
    )
    private List<Author> translators;

    @OneToMany(mappedBy = "book")
    private List<BookCopy> copies;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    private LocalDateTime deletedAt;

    public enum Audience {
        CHILDREN,
        ADULTS,
        GENERAL;

        @JsonCreator
        public static Audience fromString(String value) {
            for (Audience audience : values()) {
                if (audience.name().equalsIgnoreCase(value)) {
                    return audience;
                }
            }
            return null;
        }
    }

    public enum Format {
        HARDCOVER,
        PAPERBACK;

        @JsonCreator
        public static Format fromString(String value) {
            for (Format format : values()) {
                if (format.name().equalsIgnoreCase(value)) {
                    return format;
                }
            }
            return null;
        }
    }
}
