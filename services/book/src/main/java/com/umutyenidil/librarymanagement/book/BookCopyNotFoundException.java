package com.umutyenidil.librarymanagement.book;

public class BookCopyNotFoundException extends RuntimeException {
    public BookCopyNotFoundException(String message) {
        super(message);
    }
}
