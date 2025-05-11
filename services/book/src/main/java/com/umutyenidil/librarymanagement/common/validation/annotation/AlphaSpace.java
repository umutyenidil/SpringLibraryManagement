package com.umutyenidil.librarymanagement.common.validation.annotation;

import com.umutyenidil.librarymanagement.common.validation.validator.AlphaSpaceValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = AlphaSpaceValidator.class)
@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface AlphaSpace {
    String message() default "Must contain only letters and spaces";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
