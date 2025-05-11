package com.umutyenidil.librarymanagement.language;

import com.umutyenidil.librarymanagement.common.exception.ResourceDuplicationException;
import com.umutyenidil.librarymanagement.common.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LanguageService {

    private final LanguageRepository languageRepository;
    private final LanguageMapper languageMapper;

    public UUID saveLanguage(LanguageCreateRequest request) {

        // ayni isimli bir dil varsa hata firlat
        if (languageRepository.existsByNameIgnoreCase(request.name())) throw new ResourceDuplicationException("error.language.duplicate");

        // request dto'dan entity'e donusum yap
        var language = languageMapper.toLanguage(request);

        // dili veritabanina kaydet
        var savedLanguage = languageRepository.save(language);

        // veritabanina kaydedilen dilin id'sini dondur
        return savedLanguage.getId();
    }

    public Page<Language> findAllLanguages(Pageable pageable) {
        return languageRepository.findAll(pageable);
    }

    public LanguageResponse findLanguageById(
            UUID id
    ) {
        return languageRepository.findById(id)
                .map(languageMapper::toLanguageResponse)
                .orElseThrow(() -> new ResourceNotFoundException("error.language.notfound"));
    }

    public void deleteLanguageById(UUID id) {
        languageRepository.deleteById(id);
    }
}
