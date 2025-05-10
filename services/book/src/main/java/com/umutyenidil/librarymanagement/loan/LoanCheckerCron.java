package com.umutyenidil.librarymanagement.loan;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.UUID;

@RequiredArgsConstructor
@Slf4j
@Service
public class LoanCheckerCron {

    private final LoanService loanService;
    private final LoanPenaltyService loanPenaltyService;

    @Scheduled(cron = "*/15 * * * * *")
    public void checkLoans() {

        final var unreturnedLoanIds = loanService.findUnreturnedLoanIdsIn24Hours();

        for (UUID unreturnedLoanId : unreturnedLoanIds) {
            var loanPenalty = LoanPenalty.builder()
                    .loan(
                            Loan.builder()
                                    .id(unreturnedLoanId)
                                    .build()
                    )
                    .build();

            loanPenaltyService.saveLoanPenalty(loanPenalty);
        }
    }
}
