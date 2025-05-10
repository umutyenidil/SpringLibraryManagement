package com.umutyenidil.librarymanagement.loan;

import com.umutyenidil.librarymanagement.book.Book;
import com.umutyenidil.librarymanagement.book.BookRepository;
import com.umutyenidil.librarymanagement.book.BookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class LoanService {

    private final LoanPenaltyRepository loanPenaltyRepository;
    private final LoanRepository loanRepository;
    private final BookService bookService;

    public UUID saveLoan(LoanCreateRequest request) {

        var patronId = UUID.fromString(request.patronId());

        var patronHasUnpaidLoanPenalty =  loanPenaltyRepository.existsUnpaidPenaltyByPatronId(patronId);

        log.info("patron unpaid loan penalty status: {}", patronHasUnpaidLoanPenalty);

        if(patronHasUnpaidLoanPenalty) throw new PatronHasUnpaidPenaltyException("patron has unpaid loan penalty");

        var book = bookService.findBookById(UUID.fromString(request.bookId()));

        var borrowedAt = LocalDateTime.now();
        var dueAt = borrowedAt.plusDays(7).with(LocalTime.of(23, 59));

        var loan = Loan.builder()
                .patronId(patronId)
                .book(book)
                .borrowedAt(borrowedAt)
                .dueAt(dueAt)
                .build();

        var savedLoan = loanRepository.save(loan);

        return savedLoan.getId();
    }

    public void saveLoanPenalty(LoanPenalty loanPenalty) {

        loanPenaltyRepository.save(loanPenalty);
    }

    public List<UUID> findUnreturnedLoanIdsIn24Hours() {

        final var start = LocalDate.now().minusDays(1).atStartOfDay();
        final var end = LocalDate.now().atStartOfDay();

        return loanRepository.findUnreturnedLoanIdsWithoutPenaltyBetweenDates(start, end);
    }
}
