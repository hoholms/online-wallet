package com.online.wallet.model.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionFilterDTO {

  private BigDecimal minAmount;
  private BigDecimal maxAmount;
  private String     message;
  private Boolean    isIncome;
  private Boolean    isExpense;
  private List<Long> incomeCategories;
  private List<Long> expenseCategories;
  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
  private LocalDate  transactionDateFrom;
  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
  private LocalDate  transactionDateTo;

}

