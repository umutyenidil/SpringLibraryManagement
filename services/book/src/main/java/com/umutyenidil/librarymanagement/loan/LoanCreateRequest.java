package com.umutyenidil.librarymanagement.loan;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record LoanCreateRequest(
        @NotNull(message = "{error.loan.validation.bookcopy.barcode.notnull}")
        String bookCopyBarcode,

        String patronId
) {
}
