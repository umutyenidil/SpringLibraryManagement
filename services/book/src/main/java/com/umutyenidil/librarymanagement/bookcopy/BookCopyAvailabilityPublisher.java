package com.umutyenidil.librarymanagement.bookcopy;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

@Service
public class BookCopyAvailabilityPublisher {

    private final Sinks.Many<BookCopyAvailabilityEvent> sink =
            Sinks.many().multicast().onBackpressureBuffer();

    public void publish(BookCopyAvailabilityEvent event) {
        sink.tryEmitNext(event);
    }

    public Flux<BookCopyAvailabilityEvent> stream() {
        return sink.asFlux();
    }
}
