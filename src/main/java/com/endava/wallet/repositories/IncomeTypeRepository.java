package com.endava.wallet.repositories;

import com.endava.wallet.domain.IncomeType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IncomeTypeRepository extends JpaRepository<IncomeType, Long> {
}