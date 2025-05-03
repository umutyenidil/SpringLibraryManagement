package com.umutyenidil.librarymanagement.book;

import com.umutyenidil.librarymanagement._core.exception.BaseException;

public class BookTranslatorNotFoundException extends BaseException {
    public BookTranslatorNotFoundException(String messageCode) {
        super(messageCode);
    }
}
