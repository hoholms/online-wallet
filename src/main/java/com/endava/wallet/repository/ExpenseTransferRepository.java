package com.endava.wallet.repository;

import com.endava.wallet.entity.ExpenseTransfer;
import com.endava.wallet.entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface ExpenseTransferRepository extends JpaRepository<ExpenseTransfer, Long> {
    List<ExpenseTransfer> findExpenseTransferByProfileAndTransferDateBetween(Profile profile, LocalDate fromDate, LocalDate toDate);
}