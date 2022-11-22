package com.endava.wallet.repository;

import com.endava.wallet.entity.Profile;
import com.endava.wallet.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findTransactionByProfileOrderByIdAsc(Profile profile);

    Transaction findTransactionById(Long id);

    Transaction findTransactionByIdAndProfile(Long id, Profile profile);

    List<Transaction> findByProfileAndIsIncomeAndTransactionDateBetween(Profile profile, Boolean isIncome, LocalDate from, LocalDate to);

    @Query(value = """
            SELECT category
            FROM (SELECT category, sum(amount) as sum_amount
                  FROM transactions
                           JOIN transactions_categories tc on tc.id = transactions.category_id
                  WHERE profile_id = :cur_profile and tc.is_income = :isIncome and transaction_date between :from_date and :to_date
                  group by category) as csa
            WHERE sum_amount = (SELECT max(sum_amount) FROM (SELECT sum(amount) as sum_amount
                                                             FROM transactions
                                                                      JOIN transactions_categories tc on tc.id = transactions.category_id
                                                             WHERE profile_id = :cur_profile and tc.is_income = :isIncome and transaction_date between :from_date and :to_date
                                                             group by category) as ttsa)
            """, nativeQuery = true)
    String findMaxCategoryDateBetween(@Param("cur_profile") Profile curProfile, @Param("isIncome") Boolean isIncome, @Param("from_date") LocalDate fromDate, @Param("to_date") LocalDate toDate);

    @Query(value = """
            SELECT sum_amount
            FROM (SELECT category, sum(amount) as sum_amount
                  FROM transactions
                           JOIN transactions_categories tc on tc.id = transactions.category_id
                  WHERE profile_id = :cur_profile and tc.is_income = :isIncome and transaction_date between :from_date and :to_date
                  group by category) as csa
            WHERE sum_amount = (SELECT max(sum_amount) FROM (SELECT sum(amount) as sum_amount
                                                             FROM transactions
                                                                      JOIN transactions_categories tc on tc.id = transactions.category_id
                                                             WHERE profile_id = :cur_profile and tc.is_income = :isIncome and transaction_date between :from_date and :to_date
                                                             group by category) as ttsa)
            """, nativeQuery = true)
    BigDecimal findMaxSumDateBetween(@Param("cur_profile") Profile curProfile, @Param("isIncome") Boolean isIncome, @Param("from_date") LocalDate fromDate, @Param("to_date") LocalDate toDate);
}