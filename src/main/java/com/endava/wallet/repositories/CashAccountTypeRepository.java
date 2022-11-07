package com.endava.wallet.repositories;

import com.endava.wallet.domain.CashAccountType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CashAccountTypeRepository extends JpaRepository<CashAccountType, Long> {
}