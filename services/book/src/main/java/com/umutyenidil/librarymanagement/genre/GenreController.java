package com.umutyenidil.librarymanagement.genre;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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
}
