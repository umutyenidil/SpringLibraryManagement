package com.umutyenidil.librarymanagement.book;

import com.umutyenidil.librarymanagement._core.exception.BaseException;

public class BookAuthorNotFoundException extends BaseException {
    public BookAuthorNotFoundException(String messageCode) {
        super(messageCode);
    }
}
