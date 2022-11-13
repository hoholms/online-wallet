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
    List<Transaction> findByProfileAndTransactionDateBetweenOrderByTransactionDateAsc(Profile profile, LocalDate from, LocalDate to);

    List<Transaction> findTop9ByProfileOrderByTransactionDateAsc(Profile profile);

    List<Transaction> findByProfileAndIsIncomeAndTransactionDateBetween(Profile profile, Boolean isIncome, LocalDate from, LocalDate to);

    @Query(value = "SELECT category\n" +
            "FROM (SELECT category, sum(amount) as sum_amount\n" +
            "      FROM transactions\n" +
            "               JOIN transactions_categories tc on tc.id = transactions.category_id\n" +
            "      WHERE profile_id = :cur_profile and tc.is_income = :isIncome and transaction_date between :from_date and :to_date\n" +
            "      group by category) as csa\n" +
            "WHERE sum_amount = (SELECT max(sum_amount) FROM (SELECT sum(amount) as sum_amount\n" +
            "                                                 FROM transactions\n" +
            "                                                          JOIN transactions_categories tc on tc.id = transactions.category_id\n" +
            "                                                 WHERE profile_id = :cur_profile and tc.is_income = :isIncome and transaction_date between :from_date and :to_date\n" +
            "                                                 group by category) as ttsa)", nativeQuery = true)
    String FindMaxCategoryDateBetween(@Param("cur_profile") Profile cur_profile, @Param("isIncome") Boolean isIncome, @Param("from_date") LocalDate from_date, @Param("to_date") LocalDate to_date);

    @Query(value = "SELECT sum_amount\n" +
            "FROM (SELECT category, sum(amount) as sum_amount\n" +
            "      FROM transactions\n" +
            "               JOIN transactions_categories tc on tc.id = transactions.category_id\n" +
            "      WHERE profile_id = :cur_profile and tc.is_income = :isIncome and transaction_date between :from_date and :to_date\n" +
            "      group by category) as csa\n" +
            "WHERE sum_amount = (SELECT max(sum_amount) FROM (SELECT sum(amount) as sum_amount\n" +
            "                                                 FROM transactions\n" +
            "                                                          JOIN transactions_categories tc on tc.id = transactions.category_id\n" +
            "                                                 WHERE profile_id = :cur_profile and tc.is_income = :isIncome and transaction_date between :from_date and :to_date\n" +
            "                                                 group by category) as ttsa)", nativeQuery = true)
    BigDecimal FindMaxSumDateBetween(@Param("cur_profile") Profile cur_profile, @Param("isIncome") Boolean isIncome, @Param("from_date") LocalDate from_date, @Param("to_date") LocalDate to_date);
}