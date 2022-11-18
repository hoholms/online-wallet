package com.endava.wallet.entity.dto;

import com.endava.wallet.entity.Transaction;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * A dto for the {@link Transaction} entity
 */
@Data
public class TransactionDto implements Serializable {
    private final String category;
    private final Boolean isIncome;
    private final BigDecimal amount;
    private final String message;
    private final String transactionDate;
}