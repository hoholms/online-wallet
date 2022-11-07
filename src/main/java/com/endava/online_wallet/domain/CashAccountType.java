package com.endava.online_wallet.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "cash_account_type")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class CashAccountType {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "username")
    @ToString.Exclude
    private User username;

    @Column(name = "type_name", nullable = false, length = 50)
    private String typeName;

    @Column(name = "is_default", nullable = false)
    private Boolean isDefault = false;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        CashAccountType that = (CashAccountType) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}