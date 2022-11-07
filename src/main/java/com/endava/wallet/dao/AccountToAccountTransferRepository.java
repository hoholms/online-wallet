package com.endava.wallet.dao;

import com.endava.wallet.domain.AccountToAccountTransfer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountToAccountTransferRepository extends JpaRepository<AccountToAccountTransfer, Long> {
}