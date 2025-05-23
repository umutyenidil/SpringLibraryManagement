package com.umutyenidil.librarymanagement.common.validation.annotation;

import com.umutyenidil.librarymanagement.common.validation.validator.PasswordValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;


@Documented
@Constraint(validatedBy = PasswordValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface Password {
    String message() default "{error.auth.validation.password}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
