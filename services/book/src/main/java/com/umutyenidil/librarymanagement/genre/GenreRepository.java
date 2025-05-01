package com.umutyenidil.librarymanagement.genre;

import com.umutyenidil.librarymanagement._core.repository.SoftDeletableJpaRepository;

import java.util.UUID;

public interface GenreRepository extends SoftDeletableJpaRepository<Genre, UUID> {
    boolean existsByNameIgnoreCase(String name);
}
