package com.endava.wallet.entity.dto;

import com.endava.wallet.entity.Profile;
import com.endava.wallet.entity.Transaction;
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

    public TransactionDto toDto(Transaction transaction) {
        return TransactionDto.builder()
                .category(transaction.getCategory().getCategory())
                .transactionDate(transaction.getTransactionDate().toString())
                .amount(transaction.getAmount())
                .isIncome(transaction.getIsIncome())
                .message(transaction.getMessage())
                .build();
    }
}
