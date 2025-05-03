package com.umutyenidil.librarymanagement.book;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;
    private final BookMapper bookMapper;

    @PostMapping
    public ResponseEntity<UUID> saveBook(
            @RequestBody @Valid BookCreateRequest request
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(bookService.saveBook(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookResponse> findBookById(
            @PathVariable UUID id
    ) {
        var book = bookService.findBookById(id);

        return ResponseEntity
                .ok(bookMapper.toBookResponse(book));
    }
}
