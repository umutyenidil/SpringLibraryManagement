package com.umutyenidil.librarymanagement.loan;

import org.springframework.stereotype.Service;

@Service
public class LoanMapper {

    public LoanResponse toResponse(Loan loan) {

        return LoanResponse.builder()
                .id(loan.getId())
                .bookName(loan.getBookCopy().getBook().getName())
                .bookCopyBarcode(loan.getBookCopy().getBarcode())
                .borrowedAt(loan.getBorrowedAt())
                .dueAt(loan.getDueAt())
                .returnedAt(loan.getReturnedAt())
                .build();
    }
}
