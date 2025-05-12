package com.umutyenidil.librarymanagement.loan;

import com.umutyenidil.librarymanagement.bookcopy.BookCopyService;
import com.umutyenidil.librarymanagement.common.exception.BusinessRuleViolationException;
import com.umutyenidil.librarymanagement.common.exception.ResourceUnavailableException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.channels.FileChannel;
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
    private final BookCopyService bookCopyService;

    public UUID saveLoan(UUID patronId, LoanCreateRequest request) {

        // kullanicinin cezasi var mi diye kontrol et
        var patronHasUnpaidLoanPenalty =  loanPenaltyRepository.existsUnpaidPenaltyByPatronId(patronId);

        // cezasi vars ahata firlat
        if(patronHasUnpaidLoanPenalty) throw new BusinessRuleViolationException("error.patron.penalty.unpaid");

        // kitap kopyasini bulmaya calis, yoksa hata firlat
        var bookCopy = bookCopyService.findBookCopyByBarcode(request.bookCopyBarcode());

        // kitap kopyasi su an baskasi tarafindan odunc alinmis olarak mi gorunuyor kontrol et
        var existsActiveLoanByBookCopy = loanRepository.existsActiveLoanByBookCopyId(bookCopy.getId());

        // kitap baskasi tarafindan alinmis gorunuyor ise hata firlat
        if(existsActiveLoanByBookCopy) throw new ResourceUnavailableException("error.bookcopy.notavailable");

        // odunc alma ve son teslim tarihini olustur
        var borrowedAt = LocalDateTime.now();
        var dueAt = borrowedAt.plusDays(7).with(LocalTime.of(23, 59));

        // odunc alma operasyonunu veritabanina kaydet
        var loan = Loan.builder()
                .patronId(patronId)
                .bookCopy(bookCopy)
                .borrowedAt(borrowedAt)
                .dueAt(dueAt)
                .build();
        var savedLoan = loanRepository.save(loan);

        // odunc alma id'sini dondur
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

    @Transactional
    public UUID returnLoanByBookCopyBarcode(UUID userId, String bookCopyBarcode) {

        // kitap kopyasi varsa getir yoksa hata firlat
        var bookCopy = bookCopyService.findBookCopyByBarcode(bookCopyBarcode);

        // kitap kopyasi ve user ile olusturulmus bir odunc alma var mi kontrol et yoksa hata firlat
        var loan = loanRepository.findByBookCopyAndPatronIdAndReturnedAtIsNull(bookCopy, userId)
                .orElseThrow(() -> new ResourceUnavailableException("error.loan.notactive.or.notowned"));

        // teslim edilme tarihini guncelle ve kaydet
        loan.setReturnedAt(LocalDateTime.now());
        loanRepository.save(loan);

        return loan.getId();
    }

    public Page<Loan> findAllLoans(Pageable pageable) {

        return loanRepository.findAllByDeletedAtIsNull(pageable);
    }

    public Page<Loan> findLoansByPatronId(UUID patronId, Pageable pageable) {

        return loanRepository.findByPatronIdAndDeletedAtIsNull(patronId, pageable);
    }
}
