package com.umutyenidil.librarymanagement.author;

import com.umutyenidil.librarymanagement.language.LanguageResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/authors")
@RequiredArgsConstructor
public class AuthorController {

    private final AuthorService authorService;

    @PostMapping
    public ResponseEntity<UUID> saveAuthor(
            @RequestBody @Valid AuthorRequest request
    ) {
        return ResponseEntity.ok(authorService.saveAuthor(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AuthorResponse> getAuthor(
            @PathVariable UUID id
    ) {
        return ResponseEntity.ok(authorService.findAuthorById(id));
    }

    @GetMapping
    public ResponseEntity<Page<AuthorResponse>> findAllAuthors(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "20") int size
    ) {
        return ResponseEntity.ok(authorService.findAllAuthors(PageRequest.of(page - 1, size)));
    }
}
