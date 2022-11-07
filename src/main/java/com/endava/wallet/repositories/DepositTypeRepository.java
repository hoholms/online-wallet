package com.endava.wallet.repositories;

import com.endava.wallet.domain.DepositType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepositTypeRepository extends JpaRepository<DepositType, Long> {
}