package com.endava.wallet.repository;

import com.endava.wallet.entity.TransactionsCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionsCategoryRepository extends JpaRepository<TransactionsCategory, Long> {
    List<TransactionsCategory> findByIsIncome(boolean isIncome);
}