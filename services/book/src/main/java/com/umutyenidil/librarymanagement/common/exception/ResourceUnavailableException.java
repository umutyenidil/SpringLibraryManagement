package com.umutyenidil.librarymanagement.common.exception;

import lombok.Getter;

@Getter
public class ResourceUnavailableException extends RuntimeException {

    private final String messageCode;

    public ResourceUnavailableException(String messageCode) {
        super(messageCode);
        this.messageCode = messageCode;
    }
}
