package com.umutyenidil.librarymanagement.publisher;

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
@RequestMapping("api/v1/publishers")
@RestController
public class PublisherController {

    private final PublisherService publisherService;
    private final MessageUtil messageUtil;

    @PreAuthorize("hasRole('LIBRARIAN')")
    @PostMapping
    public ResponseEntity<SuccessResponse<UUID>> savePublisher(
            @RequestBody @Valid PublisherCreateRequest request
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(SuccessResponse.of(
                        publisherService.savePublisher(request),
                        messageUtil.getMessage("success.publisher.create")
                ));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PublisherResponse> findPublisherId(
            @PathVariable UUID id
    ) {
        return ResponseEntity.ok(publisherService.findPublisherId(id));
    }

    @GetMapping
    public ResponseEntity<Page<PublisherResponse>> findAllPublishers(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "20") int size
    ) {
        return ResponseEntity.ok(publisherService.findAllPublishers(PageRequest.of(page - 1, size)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePublisherById(
            @PathVariable UUID id
    ) {
        publisherService.deletePublisherById(id);

        return ResponseEntity.noContent().build();
    }
}
