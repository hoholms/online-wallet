package com.endava.online_wallet.domain;

import com.endava.online_wallet.domain.CashAccount;
import com.endava.online_wallet.domain.IncomeType;
import com.endava.online_wallet.domain.User;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "income_transfer")
public class IncomeTransfer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "username", nullable = false)
    private User username;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "from_income_type", nullable = false)
    private IncomeType fromIncomeType;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "to_account", nullable = false)
    private CashAccount toAccount;

    @Column(name = "amount", nullable = false)
    private BigDecimal amount;

    @Column(name = "transfer_date", nullable = false)
    private LocalDate transferDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUsername() {
        return username;
    }

    public void setUsername(User username) {
        this.username = username;
    }

    public IncomeType getFromIncomeType() {
        return fromIncomeType;
    }

    public void setFromIncomeType(IncomeType fromIncomeType) {
        this.fromIncomeType = fromIncomeType;
    }

    public CashAccount getToAccount() {
        return toAccount;
    }

    public void setToAccount(CashAccount toAccount) {
        this.toAccount = toAccount;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public LocalDate getTransferDate() {
        return transferDate;
    }

    public void setTransferDate(LocalDate transferDate) {
        this.transferDate = transferDate;
    }

}