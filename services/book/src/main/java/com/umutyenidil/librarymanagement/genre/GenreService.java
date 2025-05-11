package com.umutyenidil.librarymanagement.genre;

import com.umutyenidil.librarymanagement.common.exception.ResourceDuplicationException;
import com.umutyenidil.librarymanagement.common.exception.ResourceNotFoundException;
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
        if (genreRepository.existsByNameIgnoreCase(request.name()))
            throw new ResourceDuplicationException("error.genre.duplicate");

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
                .orElseThrow(() -> new ResourceNotFoundException("error.genre.notfound"));
    }

    public Page<Genre> findAllGenres(Pageable pageable) {

        return genreRepository.findAllByDeletedAtIsNull(pageable);
    }

    public void deleteGenreById(UUID id) {

        // soft delete icin turu kontrol et
        genreRepository.findByIdAndDeletedAtIsNull(id)
                .ifPresent(genre -> {

                    // turun deleted_at degerini guncelle ve kaydet
                    genre.setDeletedAt(LocalDateTime.now());
                    genreRepository.save(genre);
                });
    }
}
