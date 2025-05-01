package com.umutyenidil.librarymanagement._core.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.Optional;
import java.util.UUID;

@NoRepositoryBean
public interface SoftDeletableJpaRepository<T, ID> extends JpaRepository<T, ID> {
    Optional<T> findByIdAndDeletedAtIsNull(UUID id);

    Page<T> findAllByDeletedAtIsNull(Pageable pageable);
}
