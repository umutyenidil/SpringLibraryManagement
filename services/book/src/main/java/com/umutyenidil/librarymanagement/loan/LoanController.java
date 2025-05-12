package com.umutyenidil.librarymanagement.loan;

import com.umutyenidil.librarymanagement.common.dto.response.PageResponse;
import com.umutyenidil.librarymanagement.common.dto.response.SuccessResponse;
import com.umutyenidil.librarymanagement.common.util.MessageUtil;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
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
    private final LoanMapper loanMapper;

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

    @PreAuthorize("hasRole('PATRON')")
    @PatchMapping("/return/{barcode}")
    public ResponseEntity<SuccessResponse<UUID>> returnLoanByBookCopyBarcode(
            @RequestHeader("X-User-Id") UUID userId,
            @PathVariable("barcode") String bookCopyBarcode
    ) {

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(SuccessResponse.of(
                        loanService.returnLoanByBookCopyBarcode(userId, bookCopyBarcode),
                        messageUtil.getMessage("success.loan.return")
                ));
    }

    @PreAuthorize("hasRole('LIBRARIAN')")
    @GetMapping
    public ResponseEntity<PageResponse<LoanResponse>> findAllLoans(
            @RequestParam(defaultValue = "1") @Positive Integer page,
            @RequestParam(defaultValue = "20") @Positive Integer size
    ) {

        return ResponseEntity.ok(
                PageResponse.fromPage(
                        loanService.findAllLoans(PageRequest.of(page - 1, size))
                                .map(loanMapper::toResponse)
                )
        );
    }
}
