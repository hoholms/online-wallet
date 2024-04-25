package com.online.wallet.model.dto;

import org.springframework.stereotype.Component;

import com.online.wallet.model.TransactionsCategory;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class TransactionsCategoryDtoConverter {

  public TransactionsCategory fromDto(TransactionsCategoryDto categoryDto) {
    return TransactionsCategory
        .builder()
        .id(categoryDto.getId())
        .category(categoryDto.getCategory())
        .isIncome(categoryDto.getIsIncome())
        .build();
  }

}
