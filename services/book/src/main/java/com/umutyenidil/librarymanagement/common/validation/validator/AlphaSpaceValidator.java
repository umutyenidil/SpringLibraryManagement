package com.umutyenidil.librarymanagement.common.validation.validator;

import com.umutyenidil.librarymanagement.common.validation.annotation.AlphaSpace;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class AlphaSpaceValidator implements ConstraintValidator<AlphaSpace, String> {
    private static final String ALPHA_SPACE_REGEX = "^[A-Za-zçÇğĞıİöÖşŞüÜ\\s]+$";

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value != null && value.matches(ALPHA_SPACE_REGEX);
    }
}
