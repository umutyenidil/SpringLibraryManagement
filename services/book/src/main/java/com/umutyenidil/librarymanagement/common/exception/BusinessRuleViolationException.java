package com.umutyenidil.librarymanagement.common.exception;

import lombok.Getter;

@Getter
public class BusinessRuleViolationException extends RuntimeException {

    private final String messageCode;

    public BusinessRuleViolationException(String messageCode) {
        super(messageCode);
        this.messageCode = messageCode;
    }
}
