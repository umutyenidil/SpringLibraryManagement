package com.umutyenidil.librarymanagement.bookcopy;

import com.umutyenidil.librarymanagement.book.BookService;
import com.umutyenidil.librarymanagement.common.exception.ResourceNotFoundException;
import com.umutyenidil.librarymanagement.loan.LoanRepository;
import jakarta.validation.constraints.NotNull;
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
    private final BookCopyMapper bookCopyMapper;
    private final LoanRepository loanRepository;

    @Transactional
    public List<UUID> saveBookCopies(MultiBookCopyCreateRequest request) {

        // kopyalari eklenecek kitap var mi diye kontrol et, yoksa hata firlat
        var book = bookService.findBookById(request.bookId());


        // kitap kopyalarina ana kitabi ekle
        var copies = request.bookCopies()
                .stream()
                .map(bookCopyRequest -> {
                    BookCopy bookCopy = bookCopyMapper.toBookCopy(bookCopyRequest);
                    bookCopy.setBook(book);
                    bookCopy.setStatus(BookCopy.Status.AVAILABLE);
                    return bookCopy;
                })
                .toList();

        // kitap kopyalarini veritabanina kaydet
        var savedCopies = bookCopyRepository.saveAll(copies);

        // kaydedilen kitap kopyalarinin id'lerini dondur
        return savedCopies.stream()
                .map(BookCopy::getId)
                .toList();
    }

    public BookCopy findBookCopyById(UUID id) {
        return bookCopyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("error.book.notfound"));
    }

    public Boolean isBookCopyAvailableByBarcode(String barcode) {

        BookCopy bookCopy = bookCopyRepository.findByBarcode(barcode)
                .orElseThrow(() -> new ResourceNotFoundException("error.bookcopy.notfound"));

        boolean isOnLoan = loanRepository.existsByBookCopyAndReturnedAtIsNull(bookCopy) || bookCopy.getStatus().equals(BookCopy.Status.AVAILABLE);

        return !isOnLoan;
    }

    public BookCopy findBookCopyByBarcode(String barcode) {

        return bookCopyRepository.findByBarcode(barcode)
                .orElseThrow(() -> new ResourceNotFoundException("error.book.notfound"));
    }

    public BookCopy findAvailableBookCopyByBarcode(String barcode) {

        return bookCopyRepository.findByBarcodeAndStatus(barcode, BookCopy.Status.AVAILABLE)
                .orElseThrow(() -> new ResourceNotFoundException("error.bookcopy.notavailable"));

    }
}
