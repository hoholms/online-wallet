package com.endava.wallet.repository;

import com.endava.wallet.entity.Profile;
import com.endava.wallet.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByProfileAndTransactionDateBetweenOrderByTransactionDateAsc(Profile profile, LocalDate from, LocalDate to);
    List<Transaction> findTop10ByProfileOrderByTransactionDateAsc(Profile profile);
}