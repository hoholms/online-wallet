package com.endava.wallet.repositories;

import com.endava.wallet.domain.AccountToAccountTransfer;
import com.endava.wallet.domain.Attempts;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AttemptsRepository extends JpaRepository<Attempts, Long> {
    Optional<Attempts> findAttemptsByUsername(String username);
}
