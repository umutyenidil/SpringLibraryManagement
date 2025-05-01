package com.umutyenidil.librarymanagement.translator;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TranslatorService {

    private final TranslatorRepository translatorRepository;
    private final TranslatorMapper translatorMapper;

    public UUID saveTranslator(TranslatorRequest request) {
        var translator = translatorMapper.toTranslator(request);

        translatorRepository.save(translator);

        return translator.getId();
    }

    public TranslatorResponse findTranslatorById(UUID id) {
        return translatorRepository.findByIdAndDeletedAtIsNull(id)
                .map(translatorMapper::toTranslatorResponse)
                .orElseThrow(TranslatorNotFoundException::new);
    }
}
