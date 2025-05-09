package com.umutyenidil.librarymanagement.loan;

import com.umutyenidil.librarymanagement.book.Book;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequiredArgsConstructor
@RequestMapping("/api/v1/loans")
@RestController
public class LoanController {

    private final LoanService loanService;

    @PostMapping
    public ResponseEntity<UUID> saveLoan(
            @RequestHeader("X-User-Id") UUID userId,
            @RequestBody @Valid LoanCreateRequest request
    ) {
        var extendedRequest = LoanCreateRequest.builder()
                .bookId(request.bookId())
                .patronId(userId.toString())
                .build();

        return ResponseEntity
                .ok(loanService.saveLoan(extendedRequest));
    }
}
