package com.umutyenidil.librarymanagement.loan;

public class BookCopyNotAvailableException extends RuntimeException {
    public BookCopyNotAvailableException(String message) {
        super(message);
    }
}
