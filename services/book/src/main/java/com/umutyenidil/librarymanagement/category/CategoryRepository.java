package com.umutyenidil.librarymanagement.category;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CategoryRepository extends JpaRepository<Category, UUID> {
    boolean existsByNameIgnoreCase(String name);

    Optional<Category> findByIdAndDeletedAtIsNull(UUID id);

    Page<Category> findAllByDeletedAtIsNull(Pageable pageable);
}
