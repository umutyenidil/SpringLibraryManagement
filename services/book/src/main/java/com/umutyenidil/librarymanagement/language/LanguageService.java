package com.umutyenidil.librarymanagement.language;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.stream.Collectors;

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

    public Page<LanguageResponse> findAllLanguages(Pageable pageable) {
        return languageRepository.findAll(pageable)
                .map(languageMapper::toLanguageResponse);
    }

    public LanguageResponse findLanguageById(
            UUID id
    ) {
        return languageRepository.findById(id)
                .map(languageMapper::toLanguageResponse)
                .orElseThrow(LanguageNotFoundException::new);
    }

    public void deleteLanguageById(UUID id) {
        languageRepository.deleteById(id);
    }
}
