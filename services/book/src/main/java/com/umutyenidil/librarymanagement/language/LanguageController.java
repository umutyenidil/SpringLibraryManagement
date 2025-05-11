package com.umutyenidil.librarymanagement.language;

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

import java.util.UUID;

@RequiredArgsConstructor
@RequestMapping("/api/v1/languages")
@RestController
public class LanguageController {

    private final LanguageService languageService;
    private final MessageUtil messageUtil;
    private final LanguageMapper languageMapper;

    @PreAuthorize("hasRole('LIBRARIAN')")
    @PostMapping
    public ResponseEntity<SuccessResponse<UUID>> saveLanguage(
            @RequestBody @Valid LanguageCreateRequest request
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(SuccessResponse.of(
                        languageService.saveLanguage(request),
                        messageUtil.getMessage("success.language.create")
                ));
    }

    @GetMapping
    public ResponseEntity<PageResponse<LanguageResponse>> findAllLanguages(
            @RequestParam(defaultValue = "1") @Positive Integer page,
            @RequestParam(defaultValue = "20") @Positive Integer size
    ) {
        return ResponseEntity.ok(
                PageResponse.fromPage(
                        languageService.findAllLanguages(PageRequest.of(page - 1, size))
                                .map(languageMapper::toLanguageResponse)
                )
        );
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<LanguageResponse> findLanguageById(
            @PathVariable UUID id
    ) {
        return ResponseEntity.ok(languageService.findLanguageById(id));
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> deleteLanguageById(
            @PathVariable UUID id
    ) {
        languageService.deleteLanguageById(id);

        return ResponseEntity.noContent()
                .build();
    }
}
