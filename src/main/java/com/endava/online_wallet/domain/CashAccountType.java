package com.endava.online_wallet.domain;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "cash_account_type")
@Data
public class CashAccountType {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "username")
    private User username;

    @Column(name = "type_name", nullable = false, length = 50)
    private String typeName;

    @Column(name = "is_default", nullable = false)
    private Boolean isDefault = false;
}