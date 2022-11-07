package com.endava.wallet.repositories;

import com.endava.wallet.domain.DepositTransfer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepositTransferRepository extends JpaRepository<DepositTransfer, Long> {
}