CREATE TABLE auth
(
    id         UUID PRIMARY KEY,
    email      VARCHAR(255) NOT NULL UNIQUE,
    password   VARCHAR(255) NOT NULL,
    role       VARCHAR(20)  NOT NULL CHECK (role IN ('LIBRARIAN', 'PATRON')),
    status     VARCHAR(20)  NOT NULL CHECK (status IN ('ACTIVE', 'INACTIVE', 'SUSPENDED', 'DELETED')),
    created_at TIMESTAMP    NOT NULL,
    updated_at TIMESTAMP    NOT NULL,
    deleted_at TIMESTAMP
);

CREATE TABLE users
(
    id           UUID PRIMARY KEY REFERENCES auth (id) ON DELETE CASCADE,
    name         VARCHAR(255) NOT NULL,
    surname      VARCHAR(255) NOT NULL,
    phone        VARCHAR(50),
    full_address TEXT,
    created_at   TIMESTAMP    NOT NULL,
    updated_at   TIMESTAMP    NOT NULL,
    deleted_at   TIMESTAMP
);
