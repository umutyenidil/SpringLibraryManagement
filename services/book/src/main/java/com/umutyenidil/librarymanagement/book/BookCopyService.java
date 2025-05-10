package com.umutyenidil.librarymanagement.book;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BookCopyService {

    private final BookCopyRepository bookCopyRepository;
    private final BookService bookService;

    @Transactional
    public List<UUID> saveBookCopies(List<BookCopy> bookCopies) {
        var relatedBook = bookService.findBookById(bookCopies.getFirst().getBook().getId());

        if (relatedBook == null) throw new BookNotFoundException("error.book.notfound");

        List<BookCopy> savedCopies = bookCopyRepository.saveAll(bookCopies);
        return savedCopies.stream()
                .map(BookCopy::getId)
                .toList();
    }

    public BookCopy findBookCopyById(UUID id) {
        return bookCopyRepository.findById(id)
                .orElseThrow(() -> new BookCopyNotFoundException(""));
    }
}
