package com.umutyenidil.librarymanagement.publisher;

import org.springframework.stereotype.Service;

@Service
public class PublisherMapper {

    public Publisher toPublisher(PublisherCreateRequest request) {
        return Publisher.builder()
                .name(request.name())
                .build();
    }

    public Publisher toPublisher(PublisherResponse request) {
        return Publisher.builder()
                .id(request.id())
                .name(request.name())
                .build();
    }

    public PublisherResponse toPublisherResponse(Publisher publisher) {
        return PublisherResponse.builder()
                .id(publisher.getId())
                .name(publisher.getName())
                .build();
    }

}
