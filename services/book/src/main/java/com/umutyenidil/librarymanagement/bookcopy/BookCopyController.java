package com.umutyenidil.librarymanagement.bookcopy;

import com.umutyenidil.librarymanagement.common.dto.response.SuccessResponse;
import com.umutyenidil.librarymanagement.common.util.MessageUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RequestMapping("/api/v1/book-copies")
@RestController
public class BookCopyController {

    private final BookCopyService bookCopyService;
    private final BookCopyAvailabilityPublisher bookCopyAvailabilityPublisher;
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

    @GetMapping(value = "/barcode/{barcode}/available", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<BookCopyAvailabilityEvent> streamBookCopyAvailability(
            @PathVariable String barcode
    ) {

        return bookCopyAvailabilityPublisher
                .stream()
                .filter(event -> event.barcode().equals(barcode));
    }
}
