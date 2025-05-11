package com.umutyenidil.librarymanagement.author;

import com.umutyenidil.librarymanagement.common.dto.response.SuccessResponse;
import com.umutyenidil.librarymanagement.common.util.MessageUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequiredArgsConstructor
@RequestMapping("/api/v1/authors")
@RestController
public class AuthorController {

    private final AuthorService authorService;
    private final MessageUtil messageUtil;

    @PreAuthorize("hasRole('LIBRARIAN')")
    @PostMapping
    public ResponseEntity<SuccessResponse<UUID>> saveAuthor(
            @RequestBody @Valid AuthorCreateRequest request
    ) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(SuccessResponse.of(
                        authorService.saveAuthor(request),
                        messageUtil.getMessage("success.author.create")
                ));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SuccessResponse<AuthorResponse>> findAuthorById(
            @PathVariable UUID id
    ) {
        return ResponseEntity.ok(SuccessResponse.of(
                authorService.findAuthorById(id)
        ));
    }

    @GetMapping
    public ResponseEntity<Page<AuthorResponse>> findAllAuthors(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "20") int size
    ) {
        return ResponseEntity.ok(authorService.findAllAuthors(PageRequest.of(page - 1, size)));
    }
}
