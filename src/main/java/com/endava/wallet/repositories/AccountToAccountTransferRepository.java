package com.endava.wallet.repositories;

import com.endava.wallet.domain.AccountToAccountTransfer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountToAccountTransferRepository extends JpaRepository<AccountToAccountTransfer, Long> {
}