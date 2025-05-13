CREATE TABLE authors (
    id UUID PRIMARY KEY,
    name VARCHAR(255),
    surname VARCHAR(255),
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    deleted_at TIMESTAMP
);

CREATE TABLE publishers (
    id UUID PRIMARY KEY,
    name VARCHAR(255),
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    deleted_at TIMESTAMP
);

CREATE TABLE languages (
    id UUID PRIMARY KEY,
    name VARCHAR(255)
);

CREATE TABLE categories (
    id UUID PRIMARY KEY,
    name VARCHAR(255),
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    deleted_at TIMESTAMP
);

CREATE TABLE genres (
    id UUID PRIMARY KEY,
    name VARCHAR(255),
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    deleted_at TIMESTAMP
);

CREATE TABLE books (
    id UUID PRIMARY KEY,
    original_book_id UUID REFERENCES books(id),
    isbn VARCHAR(255) NOT NULL,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    audience VARCHAR(50) NOT NULL,
    language_id UUID REFERENCES languages(id),
    number_of_pages INT NOT NULL,
    publisher_id UUID NOT NULL REFERENCES publishers(id),
    format VARCHAR(50) NOT NULL,
    edition INT NOT NULL,
    publish_date DATE NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    deleted_at TIMESTAMP
);

CREATE TABLE book_copies (
    id UUID PRIMARY KEY,
    barcode VARCHAR(255) NOT NULL,
    book_id UUID REFERENCES books(id),
    acquisition_type VARCHAR(50) NOT NULL,
    condition VARCHAR(50) NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    deleted_at TIMESTAMP
);

CREATE TABLE book_genres (
    book_id UUID REFERENCES books(id),
    genre_id UUID REFERENCES genres(id),
    PRIMARY KEY (book_id, genre_id)
);

CREATE TABLE book_categories (
    book_id UUID REFERENCES books(id),
    category_id UUID REFERENCES categories(id),
    PRIMARY KEY (book_id, category_id)
);

CREATE TABLE book_authors (
    book_id UUID REFERENCES books(id),
    author_id UUID REFERENCES authors(id),
    PRIMARY KEY (book_id, author_id)
);

CREATE TABLE book_translators (
    book_id UUID REFERENCES books(id),
    translator_id UUID REFERENCES authors(id),
    PRIMARY KEY (book_id, translator_id)
);

CREATE TABLE loans (
    id UUID PRIMARY KEY,
    patron_id UUID NOT NULL,
    book_copy_id UUID REFERENCES book_copies(id),
    borrowed_at TIMESTAMP NOT NULL,
    due_at TIMESTAMP NOT NULL,
    returned_at TIMESTAMP,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    deleted_at TIMESTAMP
);

CREATE TABLE loan_penalties (
    id UUID PRIMARY KEY,
    loan_id UUID NOT NULL REFERENCES loans(id),
    paid_at TIMESTAMP,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    deleted_at TIMESTAMP
);
