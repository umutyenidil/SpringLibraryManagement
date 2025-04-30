CREATE TABLE authors
(
    id         UUID PRIMARY KEY,
    name       VARCHAR(255),
    surname    VARCHAR(255),
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    deleted_at TIMESTAMP
);