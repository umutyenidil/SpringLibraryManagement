package com.umutyenidil.librarymanagement.language;

import org.springframework.stereotype.Service;

@Service
public class LanguageMapper {

    public Language toLanguage(LanguageRequest request) {
        return Language.builder()
                .name(request.name())
                .build();
    }

    public LanguageResponse toLanguageResponse(Language language) {
        return LanguageResponse.builder()
                .id(language.getId())
                .name(language.getName())
                .build();
    }
}
