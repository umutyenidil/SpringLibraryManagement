package com.umutyenidil.librarymanagement.book;

import com.umutyenidil.librarymanagement._core.exception.BaseException;

public class BookAuthorRequiredException extends BaseException {
    public BookAuthorRequiredException(String messageCode) {
        super(messageCode);
    }

    public BookAuthorRequiredException(String messageCode, Object... args) {
        super(messageCode, args);
    }
}
