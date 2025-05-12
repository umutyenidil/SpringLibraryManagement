package com.umutyenidil.librarymanagement.bookcopy;

import com.umutyenidil.librarymanagement.common.dto.response.SuccessResponse;
import com.umutyenidil.librarymanagement.common.util.MessageUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RequestMapping("/api/v1/book-copies")
@RestController
public class BookCopyController {

    private final BookCopyService bookCopyService;
    private final BookCopyMapper bookCopyMapper;
    private final MessageUtil messageUtil;

    @PreAuthorize("hasRole('LIBRARIAN')")
    @PostMapping
    public ResponseEntity<SuccessResponse<List<UUID>>> saveBookCopies(
            @RequestBody @Valid MultiBookCopyCreateRequest request
    ) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(SuccessResponse.of(
                        bookCopyService.saveBookCopies(request),
                        messageUtil.getMessage("success.bookcopy.batch.create")
                ));
    }

    @GetMapping("/barcode/{barcode}/available")
    public ResponseEntity<SuccessResponse<Boolean>> isBookCopyAvailable(
            @PathVariable String barcode
    ) {

        var available = bookCopyService.isBookCopyAvailableByBarcode(barcode);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(SuccessResponse.of(
                        available,
                        messageUtil.getMessage(available ? "success.bookcopy.availability.true" : "success.bookcopy.availability.false")
                ));
    }
}
