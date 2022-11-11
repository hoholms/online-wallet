package com.endava.wallet.repository;

import com.endava.wallet.entity.ExpenseTransfer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExpenseTransferRepository extends JpaRepository<ExpenseTransfer, Long> {
}