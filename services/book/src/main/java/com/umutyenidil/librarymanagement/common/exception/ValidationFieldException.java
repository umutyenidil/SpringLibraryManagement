package com.umutyenidil.librarymanagement.common.exception;

import lombok.Getter;

@Getter
public class ValidationFieldException extends RuntimeException {

    private final String field;
    private final String messageCode;

    public ValidationFieldException(String field, String messageCode) {
        super(messageCode);
        this.field = field;
        this.messageCode = messageCode;
    }
}
