package com.endava.wallet.repository;

import com.endava.wallet.entity.TransactionsCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionsCategoryRepository extends JpaRepository<TransactionsCategory, Long> {

}