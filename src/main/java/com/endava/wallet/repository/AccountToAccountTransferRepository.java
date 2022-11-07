package com.endava.wallet.repository;

import com.endava.wallet.domain.AccountToAccountTransfer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountToAccountTransferRepository extends JpaRepository<AccountToAccountTransfer, Long> {
}