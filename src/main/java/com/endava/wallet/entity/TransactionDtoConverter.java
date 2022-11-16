package com.endava.wallet.entity;

import com.endava.wallet.service.TransactionsCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class TransactionDtoConverter {
    private final TransactionsCategoryService categoryService;

    public Transaction fromDto(TransactionDto transactionDto, Profile profile) {
        return Transaction.builder()
                .profile(profile)
                .category(categoryService.findByCategory(transactionDto.getCategory()))
                .isIncome(transactionDto.getIsIncome())
                .amount(transactionDto.getAmount())
                .message(transactionDto.getMessage())
                .transactionDate(LocalDate.parse(transactionDto.getTransactionDate()))
                .build();
    }
}
