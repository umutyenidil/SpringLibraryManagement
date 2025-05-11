package com.umutyenidil.librarymanagement.user;

import com.umutyenidil.librarymanagement.auth.PatronValidationGroup;
import com.umutyenidil.librarymanagement.common.validation.annotation.Alpha;
import com.umutyenidil.librarymanagement.common.validation.annotation.AlphaSpace;
import com.umutyenidil.librarymanagement.common.validation.annotation.NotBlankIfPresent;

import java.util.Optional;

public record UserUpdateRequest(

        @AlphaSpace(message = "{error.user.validation.name.alphaspace}")
        Optional<String> name,

        @Alpha(message = "{error.user.validation.surname.alpha}")
        Optional<String> surname,

        @NotBlankIfPresent(message = "{error.user.validation.phone.notnull}", groups = PatronValidationGroup.class)
        String phone,

        @NotBlankIfPresent(message = "{error.user.validation.fullAddress.notnull}", groups = PatronValidationGroup.class)
        String fullAddress
) {
}
