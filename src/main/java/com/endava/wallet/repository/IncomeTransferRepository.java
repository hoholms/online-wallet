package com.endava.wallet.repository;

import com.endava.wallet.entity.IncomeTransfer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IncomeTransferRepository extends JpaRepository<IncomeTransfer, Long> {
}