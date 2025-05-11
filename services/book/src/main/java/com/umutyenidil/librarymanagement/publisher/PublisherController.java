package com.umutyenidil.librarymanagement.publisher;

import com.umutyenidil.librarymanagement.common.dto.response.PageResponse;
import com.umutyenidil.librarymanagement.common.dto.response.SuccessResponse;
import com.umutyenidil.librarymanagement.common.util.MessageUtil;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
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
    private final PublisherMapper publisherMapper;

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
    public ResponseEntity<SuccessResponse<PublisherResponse>> findPublisherId(
            @PathVariable UUID id
    ) {
        return ResponseEntity.ok(SuccessResponse.of(
                publisherService.findPublisherId(id)
        ));
    }

    @GetMapping
    public ResponseEntity<PageResponse<PublisherResponse>> findAllPublishers(
            @RequestParam(defaultValue = "1") @Positive Integer page,
            @RequestParam(defaultValue = "20") @Positive Integer size
    ) {
        return ResponseEntity.ok(
                PageResponse.fromPage(
                        publisherService.findAllPublishers(PageRequest.of(page - 1, size))
                                .map(publisherMapper::toPublisherResponse)
                )
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePublisherById(
            @PathVariable UUID id
    ) {
        publisherService.deletePublisherById(id);

        return ResponseEntity.noContent().build();
    }
}
