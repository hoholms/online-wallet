package com.endava.wallet.entity.dto;

import com.endava.wallet.entity.Transaction;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * A dto for the {@link Transaction} entity
 */
@Data
public class TransactionDto implements Serializable {

    @NotNull(message = "Please provide a category")
    private final String category;

    private final Boolean isIncome;

    @NotNull(message = "Please provide transaction amount")
    @DecimalMax(value = "1000000.0", message = "Amount must be less than a million")
    @DecimalMin(value = "0.0", message = "Amount must be greater than 0")
    @PositiveOrZero(message = "Amount must be positive")
    @Digits(integer = 100, fraction = 2, message = "Amount must have 2 fraction digits")
    private final BigDecimal amount;

    @Length(max = 255, message = "Message is too long")
    private final String message;

    @NotBlank(message = "Please provide valid transaction date")
    private final String transactionDate;
}