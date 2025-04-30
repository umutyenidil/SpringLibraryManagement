package com.umutyenidil.librarymanagement.language;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/languages")
@RequiredArgsConstructor
public class LanguageController {
    private final LanguageService languageService;

    @PostMapping
    public ResponseEntity<UUID> createLanguage(
            @RequestBody @Valid LanguageRequest request
    ) {
        return ResponseEntity.ok(languageService.createLanguage(request));
    }

    @GetMapping
    public ResponseEntity<Page<Language>> paginateLanguages(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "20") int size
    ) {
        return ResponseEntity.ok(languageService.paginateLanguages(PageRequest.of(page - 1, size)));
    }

}
