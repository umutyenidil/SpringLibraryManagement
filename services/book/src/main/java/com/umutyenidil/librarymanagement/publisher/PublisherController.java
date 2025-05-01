package com.umutyenidil.librarymanagement.publisher;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("api/v1/publishers")
@RequiredArgsConstructor
public class PublisherController {

    private final PublisherService publisherService;

    @PostMapping
    public ResponseEntity<UUID> savePublisher(
            @RequestBody @Valid PublisherRequest request
    ) {
        return ResponseEntity.ok(publisherService.savePublisher(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PublisherResponse> findPublisherId(
            @PathVariable UUID id
    ) {
        return ResponseEntity.ok(publisherService.findPublisherId(id));
    }
}
