package com.online.wallet.model.dto;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Component;

import com.online.wallet.model.Profile;
import com.online.wallet.model.Transaction;
import com.online.wallet.service.TransactionsCategoryService;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class TransactionDtoConverter {

  private final TransactionsCategoryService categoryService;

  public Transaction fromDto(TransactionDto transactionDto, Profile profile) {
    if (transactionDto == null || profile == null) {
      return new Transaction();
    }

    return Transaction
        .builder()
        .profile(profile)
        .category(categoryService.findByCategory(transactionDto.getCategory()))
        .isIncome(transactionDto.getIsIncome())
        .amount(transactionDto.getAmount())
        .message(transactionDto.getMessage())
        .transactionDate(LocalDate.parse(transactionDto.getTransactionDate()))
        .build();
  }

  public TransactionFilterDTO toTransactionFilterDTO(TransactionDto transactionDto) {
    if (transactionDto == null) {
      return new TransactionFilterDTO();
    }

    return TransactionFilterDTO
        .builder()
        .minAmount(transactionDto.getAmount())
        .maxAmount(transactionDto.getAmount())
        .message(transactionDto.getMessage())
        .isIncome(transactionDto.getIsIncome())
        .isExpense(!transactionDto.getIsIncome())
        .incomeCategories(Boolean.TRUE.equals(transactionDto.getIsIncome()) ? List.of(categoryService
            .findByCategory(transactionDto.getCategory())
            .getId()) : Collections.emptyList())
        .expenseCategories(Boolean.FALSE.equals(transactionDto.getIsIncome()) ? List.of(categoryService
            .findByCategory(transactionDto.getCategory())
            .getId()) : Collections.emptyList())
        .transactionDateFrom(LocalDate.parse(transactionDto.getTransactionDate()))
        .transactionDateTo(LocalDate.parse(transactionDto.getTransactionDate()))
        .build();
  }

}
