package com.umutyenidil.librarymanagement.publisher;

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

    public UUID savePublisher(PublisherRequest request) {
        if(publisherRepository.existsByNameIgnoreCase(request.name())) throw new PublisherDuplicationException();

        var publisher = publisherMapper.toPublisher(request);

        publisherRepository.save(publisher);

        return publisher.getId();
    }

    public PublisherResponse findPublisherId(UUID id) {
        return publisherRepository.findByIdAndDeletedAtIsNull(id)
                .map(publisherMapper::toPublisherResponse)
                .orElseThrow(PublisherNotFoundException::new);
    }

    public Page<PublisherResponse> findAllPublishers(Pageable pageable) {
        return publisherRepository.findAllByDeletedAtIsNull(pageable)
                .map(publisherMapper::toPublisherResponse);
    }

    public void deletePublisherById(UUID id) {
        publisherRepository.findByIdAndDeletedAtIsNull(id)
                .ifPresent(publisher -> {
                    publisher.setDeletedAt(LocalDateTime.now());
                    publisherRepository.save(publisher);
                });
    }
}
