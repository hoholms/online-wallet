package com.endava.wallet.dao;

import com.endava.wallet.domain.CashAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CashAccountRepository extends JpaRepository<CashAccount, Long> {
}