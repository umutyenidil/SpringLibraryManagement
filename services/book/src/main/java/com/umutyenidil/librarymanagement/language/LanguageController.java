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
    public ResponseEntity<UUID> saveLanguage(
            @RequestBody @Valid LanguageRequest request
    ) {
        return ResponseEntity.ok(languageService.saveLanguage(request));
    }

    @GetMapping
    public ResponseEntity<Page<LanguageResponse>> findAllLanguages(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "20") int size
    ) {
        return ResponseEntity.ok(languageService.findAllLanguages(PageRequest.of(page - 1, size)));
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<LanguageResponse> findLanguageById(
            @PathVariable UUID id
    ) {
        return ResponseEntity.ok(languageService.findLanguageById(id));
    }
}
