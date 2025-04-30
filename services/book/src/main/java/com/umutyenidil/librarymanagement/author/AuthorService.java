package com.umutyenidil.librarymanagement.author;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthorService {

    private final AuthorRepository authorRepository;
    private final AuthorMapper authorMapper;

    public UUID saveAuthor(AuthorRequest request) {

        var savedAuthor = authorRepository.save(authorMapper.toAuthor(request));

        return savedAuthor.getId();
    }
}
