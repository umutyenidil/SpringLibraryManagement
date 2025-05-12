package com.umutyenidil.librarymanagement.bookcopy;

import com.umutyenidil.librarymanagement._core.repository.SoftDeletableJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface BookCopyRepository extends SoftDeletableJpaRepository<BookCopy, UUID> {
}
