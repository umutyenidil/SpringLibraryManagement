package com.umutyenidil.librarymanagement.genre;

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
@RequestMapping("/api/v1/genres")
@RestController
public class GenreController {

    private final GenreService genreService;
    private final MessageUtil messageUtil;

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
    public ResponseEntity<GenreResponse> findGenreById(
            @PathVariable UUID id
    ) {
        return ResponseEntity.ok(genreService.findGenreById(id));
    }

    @GetMapping
    public ResponseEntity<Page<GenreResponse>> findAllGenres(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "20") int size
    ) {
        return ResponseEntity.ok(genreService.findAllGenres(PageRequest.of(page - 1, size)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteGenreById(
            @PathVariable UUID id
    ) {
        genreService.deleteGenreById(id);

        return ResponseEntity.noContent().build();
    }
}
