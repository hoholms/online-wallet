package com.online.wallet.repository;

import com.online.wallet.model.TransactionsCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TransactionsCategoryRepository extends JpaRepository<TransactionsCategory, Long> {

    List<TransactionsCategory> findByIsIncome(boolean isIncome);

    Optional<TransactionsCategory> findByCategory(String category);

}