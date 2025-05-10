package com.umutyenidil.librarymanagement.loan;

public class PatronHasUnpaidPenaltyException extends RuntimeException {
    public PatronHasUnpaidPenaltyException(String message) {
        super(message);
    }
}
