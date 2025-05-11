package com.umutyenidil.librarymanagement.author;

import com.umutyenidil.librarymanagement.common.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthorService {

    private final AuthorRepository authorRepository;
    private final AuthorMapper authorMapper;

    public UUID saveAuthor(AuthorCreateRequest request) {

        // yazari kaydet
        var savedAuthor = authorRepository.save(authorMapper.toAuthor(request));

        // kaydedilen yazarin id degerini dondur
        return savedAuthor.getId();
    }

    public AuthorResponse findAuthorById(UUID id) {

        return authorRepository.findById(id)
                .map(authorMapper::toAuthorResponse)
                .orElseThrow(() -> new ResourceNotFoundException("error.author.notfound"));
    }

    public Page<Author> findAllAuthors(Pageable pageable) {

        return authorRepository.findAll(pageable);
    }
}
