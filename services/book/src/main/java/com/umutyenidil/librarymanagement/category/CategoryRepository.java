package com.umutyenidil.librarymanagement.category;

import com.umutyenidil.librarymanagement._core.repository.SoftDeletableJpaRepository;

import java.util.UUID;

public interface CategoryRepository extends SoftDeletableJpaRepository<Category, UUID> {
    boolean existsByNameIgnoreCase(String name);
}
