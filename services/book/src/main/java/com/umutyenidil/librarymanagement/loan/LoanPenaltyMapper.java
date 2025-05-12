package com.umutyenidil.librarymanagement.loan;

import org.springframework.stereotype.Service;

@Service
public class LoanPenaltyMapper {

    public LoanPenaltyResponse toResponse(LoanPenalty penalty) {
        var loan = penalty.getLoan();
        var bookCopy = loan.getBookCopy();

        return new LoanPenaltyResponse(
                penalty.getId(),
                loan.getId(),
                loan.getPatronId(),
                bookCopy.getBarcode(),
                loan.getBorrowedAt(),
                loan.getDueAt(),
                loan.getReturnedAt(),
                penalty.getCreatedAt(),
                penalty.getPaidAt()
        );
    }

}
