package com.umutyenidil.librarymanagement.genre;

import com.umutyenidil.librarymanagement.category.CategoryNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GenreService {

    private final GenreRepository genreRepository;
    private final GenreMapper genreMapper;

    public UUID saveGenre(GenreRequest request) {
        if(genreRepository.existsByNameIgnoreCase(request.name())) throw new GenreDuplicationException();

        var genre = genreMapper.toGenre(request);

        var savedGenre = genreRepository.save(genre);

        return savedGenre.getId();
    }

    public GenreResponse findGenreById(UUID id) {
        return genreRepository.findByIdAndDeletedAtIsNull(id)
                .map(genreMapper::toGenreResponse)
                .orElseThrow(GenreNotFoundException::new);
    }
}
