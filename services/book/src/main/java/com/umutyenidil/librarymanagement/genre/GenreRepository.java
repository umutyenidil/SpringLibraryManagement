package com.umutyenidil.librarymanagement.genre;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface GenreRepository extends JpaRepository<Genre, UUID> {
    boolean existsByNameIgnoreCase(String name);

    Page<Genre> findAllByDeletedAtIsNull(Pageable pageable);

    Optional<Genre> findByIdAndDeletedAtIsNull(UUID id);
}
