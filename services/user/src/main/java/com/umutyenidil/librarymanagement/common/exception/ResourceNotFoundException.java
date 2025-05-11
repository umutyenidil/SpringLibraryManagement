package com.umutyenidil.librarymanagement.common.exception;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String messageCode) {
        super(messageCode);
    }
}
