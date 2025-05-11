package com.umutyenidil.librarymanagement.common.validation.annotation;

import com.umutyenidil.librarymanagement.common.validation.validator.NotBlankIfPresentValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ FIELD, METHOD, PARAMETER, ANNOTATION_TYPE })
@Retention(RUNTIME)
@Constraint(validatedBy = NotBlankIfPresentValidator.class)
@Documented
public @interface NotBlankIfPresent {
    String message() default "Field must not be blank if provided.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

