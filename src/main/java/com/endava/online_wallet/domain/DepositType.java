package com.endava.online_wallet.domain;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "deposit_type")
@Data
public class DepositType {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "username", nullable = false)
    private User username;

    @Column(name = "type_name", nullable = false, length = 50)
    private String typeName;

    @Column(name = "is_default")
    private Boolean isDefault;
}