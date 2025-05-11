package com.umutyenidil.librarymanagement.common.exception;

import lombok.Getter;

@Getter
public class ResourceDuplicationException extends RuntimeException {

    private final String messageCode;

    public ResourceDuplicationException(String messageCode) {
        super(messageCode);
        this.messageCode = messageCode;
    }
}
