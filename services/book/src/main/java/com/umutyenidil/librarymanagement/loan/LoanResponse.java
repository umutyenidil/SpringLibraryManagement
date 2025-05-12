package com.umutyenidil.librarymanagement.loan;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
public record LoanResponse(
        UUID id,
        String bookName,
        String bookCopyBarcode,
        LocalDateTime borrowedAt,
        LocalDateTime dueAt,
        LocalDateTime returnedAt
) {}
