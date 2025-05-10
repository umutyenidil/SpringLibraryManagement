package com.umutyenidil.librarymanagement.loan;

import com.umutyenidil.librarymanagement.book.Book;
import com.umutyenidil.librarymanagement.book.BookRepository;
import com.umutyenidil.librarymanagement.book.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class LoanService {

    private final LoanRepository loanRepository;
    private final BookService bookService;

    public UUID saveLoan(LoanCreateRequest request) {

        var book = bookService.findBookById(UUID.fromString(request.bookId()));

        var borrowedAt = LocalDateTime.now();
        var dueAt = borrowedAt.plusDays(7).with(LocalTime.of(23, 59));

        var loan = Loan.builder()
                .patronId(UUID.fromString(request.patronId()))
                .book(book)
                .borrowedAt(borrowedAt)
                .dueAt(dueAt)
                .build();

        var savedLoan = loanRepository.save(loan);

        return savedLoan.getId();
    }

    public List<UUID> findUnreturnedLoanIdsIn24Hours() {

        final var start = LocalDate.now().minusDays(1).atStartOfDay();
        final var end = LocalDate.now().atStartOfDay();

        return loanRepository.findUnreturnedLoanIdsWithoutPenaltyBetweenDates(start, end);
    }
}
