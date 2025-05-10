package com.umutyenidil.librarymanagement.loan;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class LoanPenaltyService {

    private final LoanPenaltyRepository loanPenaltyRepository;

    public void saveLoanPenalty(LoanPenalty loanPenalty) {

        loanPenaltyRepository.save(loanPenalty);
    }
}
