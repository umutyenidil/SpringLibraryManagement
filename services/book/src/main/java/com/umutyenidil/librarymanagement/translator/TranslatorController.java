package com.umutyenidil.librarymanagement.translator;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/translators")
@RequiredArgsConstructor
public class TranslatorController {

    private final TranslatorService translatorService;

    @PostMapping
    public ResponseEntity<UUID> saveTranslator(
            @RequestBody @Valid TranslatorRequest request
    ) {
        return ResponseEntity.ok(translatorService.saveTranslator(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TranslatorResponse> findTranslatorById(
            @PathVariable UUID id
    ) {
        return ResponseEntity.ok(translatorService.findTranslatorById(id));
    }

    @GetMapping
    public ResponseEntity<Page<TranslatorResponse>> findAllTranslators(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "20") int size
    ) {
        return ResponseEntity.ok(translatorService.findAllTranslators(PageRequest.of(page - 1, size)));
    }
}
