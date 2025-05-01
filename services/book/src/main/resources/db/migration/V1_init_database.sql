CREATE TABLE languages
(
    id   UUID PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE publishers
(
    id         UUID PRIMARY KEY,
    name       VARCHAR(255) NOT NULL,
    created_at TIMESTAMP,
    updated_at TIMESTAMP
);

CREATE TABLE genres
(
    id         UUID PRIMARY KEY,
    name       VARCHAR(255) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    deleted_at TIMESTAMP
);

CREATE TABLE categories
(
    id         UUID PRIMARY KEY,
    name       VARCHAR(255) NOT NULL,
    created_at TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    deleted_at TIMESTAMP
);

CREATE TABLE authors
(
    id         UUID PRIMARY KEY,
    name       VARCHAR(255),
    surname    VARCHAR(255),
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    deleted_at TIMESTAMP
);

CREATE TABLE translator
(
    id         UUID PRIMARY KEY,
    name       VARCHAR(255),
    surname    VARCHAR(255),
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    deleted_at TIMESTAMP
);

CREATE TABLE book
(
    id               UUID PRIMARY KEY,
    original_book_id UUID,
    name             VARCHAR(255),
    description      TEXT,
    audience         VARCHAR(50),
    language_id      UUID NOT NULL,
    number_of_pages  INT,
    publisher_id     UUID NOT NULL,
    format           VARCHAR(50),
    edition          INT,
    publish_date     DATE,
    created_at       TIMESTAMP,
    updated_at       TIMESTAMP,
    deleted_at       TIMESTAMP,
    FOREIGN KEY (original_book_id) REFERENCES book (id),
    FOREIGN KEY (language_id) REFERENCES language (id),
    FOREIGN KEY (publisher_id) REFERENCES publisher (id)
);

CREATE TABLE book_copy
(
    id               UUID PRIMARY KEY,
    barcode          VARCHAR(50) NOT NULL,
    book_id          UUID,
    acquisition_type VARCHAR(50),
    condition        VARCHAR(50),
    status           VARCHAR(50),
    created_at       TIMESTAMP,
    updated_at       TIMESTAMP,
    deleted_at       TIMESTAMP,
    FOREIGN KEY (book_id) REFERENCES book (id)
);

CREATE TABLE book_authors
(
    book_id   UUID NOT NULL,
    author_id UUID NOT NULL,
    PRIMARY KEY (book_id, author_id),
    FOREIGN KEY (book_id) REFERENCES book (id),
    FOREIGN KEY (author_id) REFERENCES author (id)
);

CREATE TABLE book_translators
(
    book_id       UUID NOT NULL,
    translator_id UUID NOT NULL,
    PRIMARY KEY (book_id, translator_id),
    FOREIGN KEY (book_id) REFERENCES book (id),
    FOREIGN KEY (translator_id) REFERENCES translator (id)
);

CREATE TABLE book_genres
(
    book_id  UUID NOT NULL,
    genre_id UUID NOT NULL,
    PRIMARY KEY (book_id, genre_id),
    FOREIGN KEY (book_id) REFERENCES book (id),
    FOREIGN KEY (genre_id) REFERENCES genre (id)
);

CREATE TABLE book_categories
(
    book_id     UUID NOT NULL,
    category_id UUID NOT NULL,
    PRIMARY KEY (book_id, category_id),
    FOREIGN KEY (book_id) REFERENCES book (id),
    FOREIGN KEY (category_id) REFERENCES category (id)
);
