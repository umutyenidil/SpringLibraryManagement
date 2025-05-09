package com.umutyenidil.librarymanagement.book;

import com.umutyenidil.librarymanagement._core.response.PageResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @PostMapping
    public ResponseEntity<UUID> saveBook(
            @RequestBody @Valid BookCreateRequest request,
            @RequestHeader("X-User-Id") UUID userId
    ) {
        return ResponseEntity
                .accepted()
                .body(userId);
//        return ResponseEntity
//                .status(HttpStatus.CREATED)
//                .body(bookService.saveBook(request));
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
    public ResponseEntity<BookResponse> findBookById(
            @PathVariable UUID id
    ) {
        var book = bookService.findBookById(id);

        return ResponseEntity
                .ok(bookMapper.toBookResponse(book));
    }

    @GetMapping
    public ResponseEntity<Page<Book>> findAllBooks(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "20") int size
    ) {
        return ResponseEntity
                .ok(bookService.findAllBooks(PageRequest.of(page - 1, size)));
    }
}
