package com.online.wallet.repository;

import com.online.wallet.model.Profile;
import com.online.wallet.model.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    Page<Transaction> findTransactionsByProfile(Profile profile, Pageable pageable);

    Optional<Transaction> findTransactionById(Long id);

    Optional<Transaction> findTransactionByIdAndProfile(Long id, Profile profile);

    List<Transaction> findByProfileAndIsIncomeAndTransactionDateBetween(Profile profile, Boolean isIncome, LocalDate from,
                                                                        LocalDate to);

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
    String findMaxCategoryDateBetween(@Param("cur_profile") Profile curProfile, @Param("isIncome") Boolean isIncome,
                                      @Param("from_date") LocalDate fromDate, @Param("to_date") LocalDate toDate);

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
    BigDecimal findMaxSumDateBetween(@Param("cur_profile") Profile curProfile, @Param("isIncome") Boolean isIncome,
                                     @Param("from_date") LocalDate fromDate, @Param("to_date") LocalDate toDate);

    @Query(value = """
            SELECT category
            FROM transactions INNER JOIN transactions_categories tc on tc.id = transactions.category_id
            WHERE profile_id = :cur_profile AND transactions.is_income = :isIncome
            GROUP BY category""", nativeQuery = true)
    List<String> findCategoryByProfileAndIsIncome(@Param("cur_profile") Profile profile,
                                                  @Param("isIncome") Boolean isIncome);

    @Query(value = """
            SELECT sum(amount)
            FROM transactions INNER JOIN transactions_categories tc on tc.id = transactions.category_id
            WHERE profile_id = :cur_profile AND transactions.is_income = :isIncome
            GROUP BY category""", nativeQuery = true)
    List<BigDecimal> findCategorySumByProfileAndIsIncome(@Param("cur_profile") Profile profile,
                                                         @Param("isIncome") Boolean isIncome);

    @Query(value = """
            SELECT category
            FROM transactions INNER JOIN transactions_categories tc on tc.id = transactions.category_id
            WHERE profile_id = :cur_profile
            AND transactions.is_income = :isIncome
            AND transaction_date >= :d_from
            AND transaction_date <= :d_to
            GROUP BY category""", nativeQuery = true)
    List<String> findCategoryByProfileAndIsIncomeDateBetween(@Param("cur_profile") Profile profile,
                                                             @Param("isIncome") Boolean isIncome, @Param("d_from") LocalDate from, @Param("d_to") LocalDate to);

    @Query(value = """
            SELECT sum(amount)
            FROM transactions INNER JOIN transactions_categories tc on tc.id = transactions.category_id
            WHERE profile_id = :cur_profile
            AND transactions.is_income = :isIncome
            AND transaction_date >= :d_from
            AND transaction_date <= :d_to
            GROUP BY category""", nativeQuery = true)
    List<BigDecimal> findCategorySumByProfileAndIsIncomeDateBetween(@Param("cur_profile") Profile profile,
                                                                    @Param("isIncome") Boolean isIncome, @Param("d_from") LocalDate from, @Param("d_to") LocalDate to);

}
