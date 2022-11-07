package com.endava.online_wallet.domain;

import com.endava.online_wallet.domain.CashAccount;
import com.endava.online_wallet.domain.DepositType;
import com.endava.online_wallet.domain.User;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "deposit_transfer")
public class DepositTransfer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "username", nullable = false)
    private User username;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "from_account", nullable = false)
    private CashAccount fromAccount;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "to_deposit_type", nullable = false)
    private DepositType toDepositType;

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

    public CashAccount getFromAccount() {
        return fromAccount;
    }

    public void setFromAccount(CashAccount fromAccount) {
        this.fromAccount = fromAccount;
    }

    public DepositType getToDepositType() {
        return toDepositType;
    }

    public void setToDepositType(DepositType toDepositType) {
        this.toDepositType = toDepositType;
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