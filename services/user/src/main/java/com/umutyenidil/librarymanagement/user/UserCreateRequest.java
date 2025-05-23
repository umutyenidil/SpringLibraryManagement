package com.umutyenidil.librarymanagement.user;

import com.umutyenidil.librarymanagement.auth.PatronValidationGroup;
import com.umutyenidil.librarymanagement.common.validation.annotation.Alpha;
import com.umutyenidil.librarymanagement.common.validation.annotation.AlphaSpace;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record UserCreateRequest(

        @NotNull(message = "{error.user.validation.name.notnull}")
        @NotEmpty(message = "{error.user.validation.name.notnull}")
        @AlphaSpace(message = "{error.user.validation.name.alphaspace}")
        String name,

        @NotNull(message = "{error.user.validation.surname.notnull}")
        @NotEmpty(message = "{error.user.validation.surname.notnull}")
        @Alpha(message = "{error.user.validation.surname.alpha}")
        String surname,

        @NotNull(message = "{error.user.validation.phone.notnull}", groups = PatronValidationGroup.class)
        @NotEmpty(message = "{error.user.validation.phone.notnull}", groups = PatronValidationGroup.class)
        String phone,

        @NotNull(message = "{error.user.validation.fullAddress.notnull}", groups = PatronValidationGroup.class)
        @NotEmpty(message = "{error.user.validation.fullAddress.notnull}", groups = PatronValidationGroup.class)
        String fullAddress
) {
}
