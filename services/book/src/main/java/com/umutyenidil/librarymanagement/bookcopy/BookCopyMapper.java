package com.umutyenidil.librarymanagement.bookcopy;

import com.umutyenidil.librarymanagement.book.Book;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class BookCopyMapper {

    public BookCopy toBookCopy(BookCopyCreateRequest request) {
        return BookCopy.builder()
                .book(
                        Book.builder()
                                .build()
                )
                .acquisitionType(request.acquisitionType())
                .condition(request.condition())
                .build();
    }

    public BookCopyResponse toBookCopyResponse(BookCopy bookCopy) {
        return BookCopyResponse.builder()
                .barcode(bookCopy.getBarcode())
                .acquisitionType(bookCopy.getAcquisitionType())
                .condition(bookCopy.getCondition())
                .build();
    }
}
