package com.umutyenidil.librarymanagement.loan;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record LoanCreateRequest(
        @NotNull(message = "{error.loan.validation.bookcopyid.notnull}")
        String bookCopyId,

        String patronId
) {
}
