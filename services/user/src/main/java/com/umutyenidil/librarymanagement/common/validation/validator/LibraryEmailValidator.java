package com.umutyenidil.librarymanagement.common.validation.validator;

import com.umutyenidil.librarymanagement.common.validation.annotation.LibraryEmail;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class LibraryEmailValidator implements ConstraintValidator<LibraryEmail, String> {

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        return email != null && email.endsWith("@librarymanagement.com");
    }
}