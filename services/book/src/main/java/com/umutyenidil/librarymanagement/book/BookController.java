package com.umutyenidil.librarymanagement.book;

import com.umutyenidil.librarymanagement.common.dto.response.MessageResponse;
import com.umutyenidil.librarymanagement.common.dto.response.PageResponse;
import com.umutyenidil.librarymanagement.common.dto.response.SuccessResponse;
import com.umutyenidil.librarymanagement.common.util.MessageUtil;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;
    private final BookMapper bookMapper;
    private final BookCopyService bookCopyService;
    private final BookCopyMapper bookCopyMapper;
    private final MessageUtil messageUtil;

    @PreAuthorize("hasRole('LIBRARIAN')")
    @PostMapping
    public ResponseEntity<SuccessResponse<UUID>> saveBook(
            @RequestBody @Valid BookCreateRequest request
    ) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(SuccessResponse.of(
                        bookService.saveBook(request),
                        messageUtil.getMessage("success.book.create")
                ));
    }

    @PostMapping("/{book-id}/copies")
    public ResponseEntity<List<UUID>> saveBookCopies(
            @PathVariable("book-id") UUID bookId,
            @RequestBody @Valid MultiBookCopyCreateRequest request
    ) {
        var bookCopies = request
                .bookCopies()
                .stream()
                .map(bookCopyCreateRequest -> bookCopyMapper.toBookCopy(bookId, bookCopyCreateRequest))
                .toList();

        var savedBookCopyIds = bookCopyService.saveBookCopies(bookCopies);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(savedBookCopyIds);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SuccessResponse<BookResponse>> findBookById(
            @PathVariable UUID id
    ) {

        return ResponseEntity.ok(SuccessResponse.of(
                bookMapper.toBookResponse(bookService.findBookById(id))
        ));
    }

    @GetMapping
    public ResponseEntity<PageResponse<BookResponse>> findAllBooks(
            @RequestParam(defaultValue = "1") @Positive Integer page,
            @RequestParam(defaultValue = "20") @Positive Integer size
    ) {

        return ResponseEntity.ok(
                PageResponse.fromPage(
                        bookService.findAllBooks(PageRequest.of(page - 1, size))
                                .map(bookMapper::toBookResponse)
                )
        );
    }

    @GetMapping("/search")
    public ResponseEntity<PageResponse<BookResponse>> searchBooks(
            @RequestParam(defaultValue = "1") @Positive Integer page,
            @RequestParam(defaultValue = "20") @Positive Integer size,
            @ModelAttribute BookSearchRequest request
    ) {

        return ResponseEntity.ok(
                PageResponse.fromPage(
                        bookService.searchBooks(request, PageRequest.of(page - 1, size))
                                .map(bookMapper::toBookResponse)
                )
        );
    }

    @PreAuthorize("hasRole('LIBRARIAN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> deleteBookById(
            @PathVariable UUID id
    ) {
        bookService.deleteBookById(id);

        return ResponseEntity.ok(MessageResponse.of(
                messageUtil.getMessage("success.book.delete")
        ));
    }
}
