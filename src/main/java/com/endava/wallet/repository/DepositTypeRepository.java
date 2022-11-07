package com.endava.wallet.repository;

import com.endava.wallet.domain.DepositType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepositTypeRepository extends JpaRepository<DepositType, Long> {
}