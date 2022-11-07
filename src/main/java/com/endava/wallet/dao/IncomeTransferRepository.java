package com.endava.wallet.dao;

import com.endava.wallet.domain.IncomeTransfer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IncomeTransferRepository extends JpaRepository<IncomeTransfer, Long> {
}