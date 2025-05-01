package com.umutyenidil.librarymanagement.translator;

import org.springframework.stereotype.Service;

@Service
public class TranslatorMapper {
    public Translator toTranslator(TranslatorRequest request) {
        return Translator.builder()
                .name(request.name())
                .surname(request.surname())
                .build();
    }

    public TranslatorResponse toTranslatorResponse(Translator translator) {
        return TranslatorResponse.builder()
                .id(translator.getId())
                .name(translator.getName())
                .surname(translator.getSurname())
                .build();
    }
}
