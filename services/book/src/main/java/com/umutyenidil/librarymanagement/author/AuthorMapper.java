package com.umutyenidil.librarymanagement.author;

import org.springframework.stereotype.Service;

@Service
public class AuthorMapper {

    public Author toAuthor(AuthorRequest request) {
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
}
