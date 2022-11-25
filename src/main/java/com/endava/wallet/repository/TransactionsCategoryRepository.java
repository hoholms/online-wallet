package com.endava.wallet.repository;

import com.endava.wallet.entity.TransactionsCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TransactionsCategoryRepository extends JpaRepository<TransactionsCategory, Long> {

    List<TransactionsCategory> findByIsIncome(boolean isIncome);

    Optional<TransactionsCategory> findByCategory(String category);

}