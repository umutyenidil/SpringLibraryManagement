package com.umutyenidil.librarymanagement.publisher;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface PublisherRepository extends JpaRepository<Publisher, UUID> {

    boolean existsByNameIgnoreCase(String name);

    Optional<Publisher> findByIdAndDeletedAtIsNull(UUID id);

    Page<Publisher> findAllByDeletedAtIsNull(Pageable pageable);
}
