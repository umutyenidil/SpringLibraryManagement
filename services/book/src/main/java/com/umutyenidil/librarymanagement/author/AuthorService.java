package com.umutyenidil.librarymanagement.author;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

    public AuthorResponse findAuthorById(UUID id) {
        return authorRepository.findById(id)
                .map(authorMapper::toAuthorResponse)
                .orElseThrow(AuthorNotFoundException::new);
    }

    public Page<AuthorResponse> findAllAuthors(Pageable pageable) {
        return authorRepository.findAll(pageable)
                .map(authorMapper::toAuthorResponse);
    }
}
