package com.umutyenidil.librarymanagement.auth;

import com.umutyenidil.librarymanagement.common.validation.annotation.LibraryEmail;
import com.umutyenidil.librarymanagement.common.validation.annotation.Password;
import com.umutyenidil.librarymanagement.user.UserCreateRequest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Builder;

@Builder
public record RegisterRequest(

        @NotEmpty(message = "{error.auth.validation.email.notempty}")
        @NotBlank(message = "{error.auth.validation.email.notempty}")
        @LibraryEmail(message = "{error.auth.validation.email.libraryemail}")
        @Email(message = "{error.auth.validation.email}")
        String email,

        @NotEmpty(message = "{error.auth.validation.password.notempty}")
        @NotBlank(message = "{error.auth.validation.password.notempty}")
        @Size(min = 8, message = "{error.auth.validation.password.size}")
        @Password(message = "{error.auth.validation.password}")
        String password,

        @NotNull(message = "{error.auth.validation.userdetail.notnull}")
        @Valid
        UserCreateRequest userDetail
) {
}
