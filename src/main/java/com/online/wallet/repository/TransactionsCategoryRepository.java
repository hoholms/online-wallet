package com.online.wallet.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.online.wallet.model.TransactionsCategory;

public interface TransactionsCategoryRepository extends JpaRepository<TransactionsCategory, Long> {

  List<TransactionsCategory> findByIsIncome(boolean isIncome);

  Optional<TransactionsCategory> findByCategory(String category);

}