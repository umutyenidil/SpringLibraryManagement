package com.umutyenidil.librarymanagement.author;

import org.springframework.stereotype.Service;

@Service
public class AuthorMapper {

    Author toAuthor(AuthorRequest request) {
        return Author.builder()
                .name(request.name())
                .surname(request.surname())
                .build();
    }
}
