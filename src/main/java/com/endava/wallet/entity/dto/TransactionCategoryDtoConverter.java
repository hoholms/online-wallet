package com.endava.wallet.entity.dto;

import com.endava.wallet.entity.TransactionsCategory;
import com.endava.wallet.service.TransactionsCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TransactionCategoryDtoConverter {
    public TransactionsCategory fromDto(TransactionsCategoryDto categoryDto) {
        return TransactionsCategory.builder()
                .id(categoryDto.getId())
                .category(categoryDto.getCategory())
                .isIncome(categoryDto.getIsIncome())
                .build();
    }
}
