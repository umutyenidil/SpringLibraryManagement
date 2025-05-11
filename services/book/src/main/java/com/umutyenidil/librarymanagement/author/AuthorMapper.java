package com.umutyenidil.librarymanagement.author;

import org.springframework.stereotype.Service;

@Service
public class AuthorMapper {

    public Author toAuthor(AuthorCreateRequest request) {
        return Author.builder()
                .name(request.name())
                .surname(request.surname())
                .build();
    }

    public AuthorResponse toAuthorResponse(Author author) {
        return AuthorResponse.builder()
                .id(author.getId())
                .name(author.getName())
                .surname(author.getSurname())
                .build();
    }

    public Author toAuthor(AuthorResponse response) {
        return Author.builder()
                .id(response.id())
                .name(response.name())
                .surname(response.surname())
                .build();
    }
}
