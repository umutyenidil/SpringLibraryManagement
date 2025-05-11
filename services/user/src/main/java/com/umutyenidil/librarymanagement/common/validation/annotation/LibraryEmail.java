package com.umutyenidil.librarymanagement.common.validation.annotation;

import com.umutyenidil.librarymanagement.common.validation.validator.LibraryEmailValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = LibraryEmailValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface LibraryEmail {
    String message() default "Email must be from domain @librarymanagement.com";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
