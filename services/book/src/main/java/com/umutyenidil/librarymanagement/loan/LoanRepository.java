package com.umutyenidil.librarymanagement.loan;

import com.umutyenidil.librarymanagement.bookcopy.BookCopy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface LoanRepository extends JpaRepository<Loan, UUID> {

    @Query("""
            SELECT l.id FROM Loan l
            WHERE l.dueAt BETWEEN :start AND :end
            AND l.returnedAt IS NULL
            AND NOT EXISTS (
                SELECT p.id FROM LoanPenalty p WHERE p.loan.id = l.id
            )
            """)
    List<UUID> findUnreturnedLoanIdsWithoutPenaltyBetweenDates(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end);

    @Query("""
    SELECT COUNT(l) > 0
    FROM Loan l
    WHERE l.bookCopy.id = :bookCopyId
    AND l.returnedAt IS NULL
    """)
    boolean existsActiveLoanByBookCopyId(@Param("bookCopyId") UUID bookCopyId);

    boolean existsByBookCopyAndReturnedAtIsNull(BookCopy bookCopy);
}
