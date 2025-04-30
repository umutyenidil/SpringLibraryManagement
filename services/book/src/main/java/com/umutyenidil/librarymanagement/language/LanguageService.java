package com.umutyenidil.librarymanagement.language;

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

    public UUID saveLanguage(LanguageRequest request) {
        if (languageRepository.existsByNameIgnoreCase(request.name())) throw new LanguageDuplicatonException();

        var language = languageMapper.toLanguage(request);

        var savedLanguage = languageRepository.save(language);

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
                .orElseThrow(LanguageNotFoundException::new);
    }
}
