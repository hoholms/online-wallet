package com.online.wallet.model;

import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Transaction that = (Transaction) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}