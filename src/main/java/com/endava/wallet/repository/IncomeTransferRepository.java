package com.endava.wallet.repository;

import com.endava.wallet.entity.IncomeTransfer;
import com.endava.wallet.entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface IncomeTransferRepository extends JpaRepository<IncomeTransfer, Long> {
    List<IncomeTransfer> findIncomeTransferByProfileAndTransferDateBetween(Profile profile, LocalDate fromDate, LocalDate toDate);
}