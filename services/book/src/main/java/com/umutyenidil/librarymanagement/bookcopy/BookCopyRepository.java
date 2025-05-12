package com.umutyenidil.librarymanagement.bookcopy;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface BookCopyRepository extends JpaRepository<BookCopy, UUID> {

    Optional<BookCopy> findByBarcode(String barcode);
}
