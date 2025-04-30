package com.umutyenidil.librarymanagement.language;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface LanguageRepository extends JpaRepository<Language, UUID> {
    boolean existsByNameIgnoreCase(String name);
}
