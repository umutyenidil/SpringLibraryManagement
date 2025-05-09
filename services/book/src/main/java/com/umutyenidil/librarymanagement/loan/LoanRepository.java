package com.umutyenidil.librarymanagement.loan;

import com.umutyenidil.librarymanagement._core.repository.SoftDeletableJpaRepository;

import java.util.UUID;

public interface LoanRepository extends SoftDeletableJpaRepository<Loan, UUID> {
}
