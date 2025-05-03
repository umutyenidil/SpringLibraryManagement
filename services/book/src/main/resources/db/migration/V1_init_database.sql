CREATE TABLE languages
(
    id   UUID PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE publishers
(
    id         UUID PRIMARY KEY,
    name       VARCHAR(255) NOT NULL,
    created_at TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    deleted_at TIMESTAMP             DEFAULT NULL
);

CREATE TABLE genres
(
    id         UUID PRIMARY KEY,
    name       VARCHAR(255) NOT NULL,
    created_at TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    deleted_at TIMESTAMP             DEFAULT NULL
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

CREATE TABLE translators
(
    id         UUID PRIMARY KEY,
    name       VARCHAR(255),
    surname    VARCHAR(255),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    deleted_at TIMESTAMP          DEFAULT NULL
);

CREATE TABLE books
(
    id               UUID PRIMARY KEY,
    original_book_id UUID DEFAULT NULL,
    name             VARCHAR(255) NOT NULL,
    description      TEXT DEFAULT NULL,
    audience         VARCHAR(255) NOT NULL,
    language_id      UUID         NOT NULL,
    number_of_pages  INT          NOT NULL,
    publisher_id     UUID         NOT NULL,
    format           VARCHAR(255) NOT NULL,
    edition          INT          NOT NULL,
    publish_date     DATE         NOT NULL,
    created_at       TIMESTAMP,
    updated_at       TIMESTAMP,
    deleted_at       TIMESTAMP,
    FOREIGN KEY (original_book_id) REFERENCES books (id),
    FOREIGN KEY (language_id) REFERENCES languages (id),
    FOREIGN KEY (publisher_id) REFERENCES publishers (id)
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
