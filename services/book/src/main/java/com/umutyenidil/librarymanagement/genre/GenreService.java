package com.umutyenidil.librarymanagement.genre;

import com.umutyenidil.librarymanagement.common.exception.ResourceDuplicationException;
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

    public UUID saveGenre(GenreCreateRequest request) {

        // ayni isimli baska bir tur var ise hata firlat
        if (genreRepository.existsByNameIgnoreCase(request.name())) throw new ResourceDuplicationException("error.genre.duplicate");

        // istek turunu istek nesnesine donustur
        var genre = genreMapper.toGenre(request);

        // turu veritabanina kaydet
        var savedGenre = genreRepository.save(genre);

        // veritabanina kayit edilen tur id degerini dondur
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
