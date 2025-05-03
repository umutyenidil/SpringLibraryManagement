package com.umutyenidil.librarymanagement.book;

import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class BookCopyMapper {

    public BookCopy toBookCopy(UUID bookId, BookCopyCreateRequest request) {
        return BookCopy.builder()
                .book(
                        Book.builder()
                                .id(bookId)
                                .build()
                )
                .acquisitionType(request.acquisitionType())
                .condition(request.condition())
                .status(request.status())
                .build();
    }
}
