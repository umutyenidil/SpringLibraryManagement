package com.umutyenidil.librarymanagement.translator;

import com.umutyenidil.librarymanagement._core.repository.SoftDeletableJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TranslatorRepository extends SoftDeletableJpaRepository<Translator, UUID> {
}
