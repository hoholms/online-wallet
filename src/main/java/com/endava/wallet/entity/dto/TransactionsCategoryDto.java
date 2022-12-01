package com.endava.wallet.entity.dto;

import lombok.Data;

@Data
public class TransactionsCategoryDto {
    private final Long id;
    private final String category;
    private final Boolean isIncome;
}
