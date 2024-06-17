package com.online.wallet.model.dto;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.io.Serializable;
import java.math.BigDecimal;

import org.hibernate.validator.constraints.Length;

import com.online.wallet.model.Transaction;
import lombok.Builder;
import lombok.Data;

/**
 * A dto for the {@link Transaction} entity
 */
@Data
@Builder
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
