package com.endava.wallet.dao;

import com.endava.wallet.domain.DepositTransfer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepositTransferRepository extends JpaRepository<DepositTransfer, Long> {
}