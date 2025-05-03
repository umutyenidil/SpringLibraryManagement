package com.umutyenidil.librarymanagement.book;

import com.umutyenidil.librarymanagement._core.repository.SoftDeletableJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface BookRepository extends SoftDeletableJpaRepository<Book, UUID> {
}
