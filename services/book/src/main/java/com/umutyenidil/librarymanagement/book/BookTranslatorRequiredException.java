package com.umutyenidil.librarymanagement.book;

import com.umutyenidil.librarymanagement._core.exception.BaseException;

public class BookTranslatorRequiredException extends BaseException {
    public BookTranslatorRequiredException(String messageCode) {
        super(messageCode);
    }
}
