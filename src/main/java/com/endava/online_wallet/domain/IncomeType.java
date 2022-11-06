package com.endava.online_wallet.domain;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "income_type")
@Data
public class IncomeType {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "username", nullable = false)
    private User username;

    @Column(name = "type", nullable = false, length = 50)
    private String type;

    @Column(name = "is_default", nullable = false)
    private Boolean isDefault = false;
}