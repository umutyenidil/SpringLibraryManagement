package com.umutyenidil.librarymanagement.loan;

import com.umutyenidil.librarymanagement.book.Book;
import com.umutyenidil.librarymanagement.common.dto.response.SuccessResponse;
import com.umutyenidil.librarymanagement.common.util.MessageUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequiredArgsConstructor
@RequestMapping("/api/v1/loans")
@RestController
public class LoanController {

    private final LoanService loanService;
    private final MessageUtil messageUtil;

    @PreAuthorize("hasRole('PATRON')")
    @PostMapping
    public ResponseEntity<SuccessResponse<UUID>> saveLoan(
            @RequestHeader("X-User-Id") UUID userId,
            @RequestBody @Valid LoanCreateRequest request
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(SuccessResponse.of(
                        loanService.saveLoan(userId, request),
                        messageUtil.getMessage("success.loan.create")
                ));
    }
}
