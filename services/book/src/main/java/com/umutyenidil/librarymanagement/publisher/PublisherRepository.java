package com.umutyenidil.librarymanagement.publisher;

import com.umutyenidil.librarymanagement._core.repository.SoftDeletableJpaRepository;

import java.util.UUID;

public interface PublisherRepository extends SoftDeletableJpaRepository<Publisher, UUID> {

    boolean existsByNameIgnoreCase(String name);
}
