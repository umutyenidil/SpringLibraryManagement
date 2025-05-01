package com.umutyenidil.librarymanagement.publisher;

import org.springframework.stereotype.Service;

@Service
public class PublisherMapper {

    public Publisher toPublisher(PublisherRequest request) {
        return Publisher.builder()
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
