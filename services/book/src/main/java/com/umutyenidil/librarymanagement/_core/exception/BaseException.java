package com.umutyenidil.librarymanagement._core.exception;

import lombok.Getter;

@Getter
public abstract class BaseException extends RuntimeException {

    private final String messageCode;
    private final Object[] args;

    public BaseException(String messageCode) {
        this(messageCode, (Object[]) null);
    }

    public BaseException(String messageCode, Object... args) {
        super(messageCode);
        this.messageCode = messageCode;
        this.args = args;
    }
}
