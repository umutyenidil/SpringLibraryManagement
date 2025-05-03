package com.umutyenidil.librarymanagement.book;

import lombok.Getter;

@Getter
public class BookNotFoundException extends RuntimeException {
    private final String messageCode;

    public BookNotFoundException(String messageCode) {
        super();

        this.messageCode = messageCode;
    }
}
