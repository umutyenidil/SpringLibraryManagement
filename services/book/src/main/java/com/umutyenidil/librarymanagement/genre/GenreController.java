package com.umutyenidil.librarymanagement.genre;

import com.umutyenidil.librarymanagement.common.dto.response.MessageResponse;
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
@RequestMapping("/api/v1/genres")
@RestController
public class GenreController {

    private final GenreService genreService;
    private final MessageUtil messageUtil;
    private final GenreMapper genreMapper;

    @PreAuthorize("hasRole('LIBRARIAN')")
    @PostMapping
    public ResponseEntity<SuccessResponse<UUID>> saveGenre(
            @RequestBody @Valid GenreCreateRequest request
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(SuccessResponse.of(genreService.saveGenre(request), messageUtil.getMessage("success.genre.create")));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SuccessResponse<GenreResponse>> findGenreById(
            @PathVariable UUID id
    ) {
        return ResponseEntity
                .ok(SuccessResponse.of(genreService.findGenreById(id)));
    }

    @GetMapping
    public ResponseEntity<PageResponse<GenreResponse>> findAllGenres(
            @RequestParam(defaultValue = "1") @Positive Integer page,
            @RequestParam(defaultValue = "20") @Positive Integer size
    ) {
        return ResponseEntity.ok(
                PageResponse.fromPage(
                        genreService.findAllGenres(PageRequest.of(page - 1, size))
                                .map(genreMapper::toGenreResponse)
                )
        );
    }

    @PreAuthorize("hasRole('LIBRARIAN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteGenreById(
            @PathVariable UUID id
    ) {
        genreService.deleteGenreById(id);

        return ResponseEntity.ok(
                MessageResponse.of(messageUtil.getMessage("success.genre.delete"))
        );
    }
}
