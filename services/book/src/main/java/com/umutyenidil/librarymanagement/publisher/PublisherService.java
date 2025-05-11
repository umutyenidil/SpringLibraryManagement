package com.umutyenidil.librarymanagement.publisher;

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
public class PublisherService {

    private final PublisherRepository publisherRepository;
    private final PublisherMapper publisherMapper;

    public UUID savePublisher(PublisherCreateRequest request) {

        // ayni isimli bir yayin evi varsa hata firlat
        if (publisherRepository.existsByNameIgnoreCase(request.name()))
            throw new ResourceDuplicationException("error.publisher.duplicate");

        // yayin evi create requesti entity'e donustur
        var publisher = publisherMapper.toPublisher(request);

        // yayin evini veritabanina kaydet
        publisherRepository.save(publisher);

        // kaydedilen yayin evinin id degerini dondur
        return publisher.getId();
    }

    public PublisherResponse findPublisherId(UUID id) {
        return publisherRepository.findByIdAndDeletedAtIsNull(id)
                .map(publisherMapper::toPublisherResponse)
                .orElseThrow(() -> new ResourceNotFoundException("error.publisher.notfound"));
    }

    public Page<Publisher> findAllPublishers(Pageable pageable) {
        return publisherRepository.findAllByDeletedAtIsNull(pageable);
    }

    public void deletePublisherById(UUID id) {

        // kayitli silinmemis bir yayin evi var mi diye kontrol et
        publisherRepository.findByIdAndDeletedAtIsNull(id)
                .ifPresent(publisher -> {

                    // deleted_at degerini guncelle ve kaydet
                    publisher.setDeletedAt(LocalDateTime.now());
                    publisherRepository.save(publisher);
                });
    }
}
