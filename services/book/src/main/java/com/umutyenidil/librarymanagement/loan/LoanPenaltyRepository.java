package com.umutyenidil.librarymanagement.loan;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface LoanPenaltyRepository extends JpaRepository<LoanPenalty, UUID> {

    @Query("SELECT COUNT(lp) > 0 FROM LoanPenalty lp WHERE lp.loan.patronId = :patronId AND lp.paidAt IS NULL")
    boolean existsUnpaidPenaltyByPatronId(@Param("patronId") UUID patronId);
}