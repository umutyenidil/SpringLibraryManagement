package com.umutyenidil.librarymanagement.translator;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
