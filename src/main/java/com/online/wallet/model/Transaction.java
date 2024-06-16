package com.online.wallet.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

import org.hibernate.Hibernate;
import org.hibernate.validator.constraints.Length;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "transactions")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Transaction {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  private Long id;

  @ManyToOne(fetch = FetchType.EAGER, optional = false)
  @JoinColumn(name = "profile_id", nullable = false)
  @ToString.Exclude
  private Profile profile;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "category_id", nullable = false)
  @ToString.Exclude
  @NotNull(message = "Please provide a category")
  private TransactionsCategory category;

  @Column(name = "is_income", nullable = false)
  private Boolean isIncome = false;

  @Column(name = "amount", nullable = false)
  @NotNull(message = "Please provide transaction amount")
  @DecimalMax(value = "1000000.0", message = "Amount must be less than a million")
  @DecimalMin(value = "0.0", message = "Amount must be greater than 0")
  @PositiveOrZero(message = "Amount must be positive")
  @Digits(integer = 100, fraction = 2, message = "Amount must have 2 fraction digits")
  private BigDecimal amount;

  @Column(name = "message")
  @Length(max = 255, message = "Message is too long")
  private String message;

  @Column(name = "transaction_date", nullable = false)
  @NotNull(message = "Please provide valid transaction date")
  private LocalDate transactionDate;

  @Override
  public int hashCode() {
    return getClass().hashCode();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
      return false;
    }
    Transaction that = (Transaction) o;
    return id != null && Objects.equals(id, that.id);
  }

}
