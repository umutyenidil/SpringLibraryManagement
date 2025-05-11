package com.umutyenidil.librarymanagement.genre;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GenreService {

    private final GenreRepository genreRepository;
    private final GenreMapper genreMapper;

    public UUID saveGenre(GenreRequest request) {
        if (genreRepository.existsByNameIgnoreCase(request.name())) throw new GenreDuplicationException();

        var genre = genreMapper.toGenre(request);

        var savedGenre = genreRepository.save(genre);

        return savedGenre.getId();
    }

    public GenreResponse findGenreById(UUID id) {
        return genreRepository.findByIdAndDeletedAtIsNull(id)
                .map(genreMapper::toGenreResponse)
                .orElseThrow(GenreNotFoundException::new);
    }

    public Page<GenreResponse> findAllGenres(Pageable pageable) {
        return genreRepository.findAllByDeletedAtIsNull(pageable)
                .map(genreMapper::toGenreResponse);
    }

    public void deleteGenreById(UUID id) {
        genreRepository.findByIdAndDeletedAtIsNull(id)
                .ifPresent(genre -> {
                    genre.setDeletedAt(LocalDateTime.now());
                    genreRepository.save(genre);
                });
    }
}
