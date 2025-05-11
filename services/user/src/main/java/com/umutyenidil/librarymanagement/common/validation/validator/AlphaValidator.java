package com.umutyenidil.librarymanagement.common.validation.validator;

import com.umutyenidil.librarymanagement.common.validation.annotation.Alpha;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class AlphaValidator implements ConstraintValidator<Alpha, String> {
    private static final String ALPHA_REGEX = "^[A-Za-zçÇğĞıİöÖşŞüÜ]+$";

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value != null && value.matches(ALPHA_REGEX);
    }
}
