package com.umutyenidil.librarymanagement.language;

import org.springframework.stereotype.Service;

@Service
public class LanguageMapper {

    public Language toLanguage(LanguageCreateRequest request) {
        return Language.builder()
                .name(request.name())
                .build();
    }

    public Language toLanguage(LanguageResponse response) {
        return Language.builder()
                .id(response.id())
                .name(response.name())
                .build();
    }

    public LanguageResponse toLanguageResponse(Language language) {
        return LanguageResponse.builder()
                .id(language.getId())
                .name(language.getName())
                .build();
    }
}
