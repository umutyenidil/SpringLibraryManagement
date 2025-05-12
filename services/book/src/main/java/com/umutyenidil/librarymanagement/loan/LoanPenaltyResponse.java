package com.umutyenidil.librarymanagement.loan;

import java.time.LocalDateTime;
import java.util.UUID;

public record LoanPenaltyResponse(
        UUID id,
        UUID loanId,
        UUID patronId,
        String barcode,
        LocalDateTime borrowedAt,
        LocalDateTime dueAt,
        LocalDateTime returnedAt,
        LocalDateTime penaltyCreatedAt,
        LocalDateTime penaltyPaidAt
) {}

