package com.umutyenidil.librarymanagement.genre;

import org.springframework.stereotype.Service;

@Service
public class GenreMapper {

    public Genre toGenre(GenreCreateRequest request) {
        return Genre.builder()
                .name(request.name())
                .build();
    }

    public Genre toGenre(GenreResponse response) {
        return Genre.builder()
                .id(response.id())
                .name(response.name())
                .build();
    }

    public GenreResponse toGenreResponse(Genre genre) {
        return GenreResponse.builder()
                .id(genre.getId())
                .name(genre.getName())
                .build();
    }
}
