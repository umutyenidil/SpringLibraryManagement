package com.umutyenidil.librarymanagement.genre;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/genres")
@RequiredArgsConstructor
public class GenreController {

    private final GenreService genreService;

    @PostMapping
    public ResponseEntity<UUID> saveGenre(
            @RequestBody @Valid GenreRequest request
    ) {
        return ResponseEntity.ok(genreService.saveGenre(request));
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
}
