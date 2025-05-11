package com.umutyenidil.librarymanagement.common.exception;

import lombok.Getter;

@Getter
public class ResourceNotFoundException extends RuntimeException {

    private final String messageCode;

    public ResourceNotFoundException(String messageCode) {
        super(messageCode);
        this.messageCode = messageCode;
    }
}
