package com.umutyenidil.librarymanagement.translator;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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

    public Page<TranslatorResponse> findAllTranslators(Pageable pageable) {
        return translatorRepository.findAllByDeletedAtIsNull(pageable)
                .map(translatorMapper::toTranslatorResponse);
    }

    public void deleteTranslatorById(UUID id) {
        translatorRepository.findByIdAndDeletedAtIsNull(id)
                .ifPresent(translator -> {
                    translator.setDeletedAt(LocalDateTime.now());
                    translatorRepository.save(translator);
                });
    }
}
