package com.umutyenidil.librarymanagement.genre;

import org.springframework.stereotype.Service;

@Service
public class GenreMapper {

    public Genre toGenre(GenreRequest request) {
        return Genre.builder()
                .name(request.name())
                .build();
    }

    public GenreResponse toGenreResponse(Genre genre) {
        return GenreResponse.builder()
                .id(genre.getId())
                .name(genre.getName())
                .build();
    }
}
