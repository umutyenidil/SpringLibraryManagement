package com.umutyenidil.librarymanagement.loan;

import com.umutyenidil.librarymanagement._core.repository.SoftDeletableJpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface LoanPenaltyRepository extends SoftDeletableJpaRepository<LoanPenalty, UUID> {
}
