package com.umutyenidil.librarymanagement.language;

import org.springframework.stereotype.Service;

@Service
public class LanguageMapper {

    public Language toLanguage(LanguageRequest request) {
        return Language.builder()
                .name(request.name())
                .build();
    }
}
