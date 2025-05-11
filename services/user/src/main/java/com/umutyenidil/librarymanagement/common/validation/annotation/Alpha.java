package com.umutyenidil.librarymanagement.common.validation.annotation;

import com.umutyenidil.librarymanagement.common.validation.validator.AlphaValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = AlphaValidator.class)
@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface Alpha {
    String message() default "Must contain only letters";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
