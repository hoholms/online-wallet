package com.endava.wallet.repository;

import com.endava.wallet.entity.IncomeCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IncomeCategoryRepository extends JpaRepository<IncomeCategory, Long> {
}