package com.endava.online_wallet.domain;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "deposit_transfer")
@Data
public class DepositTransfer {
    @Id
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
    private Instant transferDate;
}