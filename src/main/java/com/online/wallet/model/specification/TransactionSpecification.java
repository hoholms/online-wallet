package com.online.wallet.model.specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.online.wallet.model.Transaction;
import com.online.wallet.model.dto.TransactionFilterDTO;
import lombok.experimental.UtilityClass;

@UtilityClass
public class TransactionSpecification {

  private static final String PROFILE          = "profile";
  private static final String ID               = "id";
  private static final String AMOUNT           = "amount";
  private static final String MESSAGE          = "message";
  private static final String IS_INCOME        = "isIncome";
  private static final String CATEGORY         = "category";
  private static final String TRANSACTION_DATE = "transactionDate";

  public static Specification<Transaction> getTransactionSpecification(TransactionFilterDTO filterDTO, Long profileId) {
    return (root, query, criteriaBuilder) -> {
      List<Predicate> predicates = new ArrayList<>();
      PredicateBuilder<Transaction> builder = new PredicateBuilder<>(root, criteriaBuilder, predicates);

      builder
          .equal(PROFILE, ID, profileId)
          .range(AMOUNT, filterDTO.getMinAmount(), filterDTO.getMaxAmount())
          .like(MESSAGE, filterDTO.getMessage())
          .booleanCondition(IS_INCOME, filterDTO.getIsIncome(), filterDTO.getIsExpense())
          .inCategories(CATEGORY, filterDTO.getIncomeCategories(), filterDTO.getExpenseCategories())
          .dateRange(TRANSACTION_DATE, filterDTO.getTransactionDateFrom(), filterDTO.getTransactionDateTo());

      return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    };
  }

  private record PredicateBuilder<T>(Root<T> root, CriteriaBuilder criteriaBuilder, List<Predicate> predicates) {

    public PredicateBuilder<T> equal(String attribute, String subAttribute, Object value) {
        if (value != null) {
          predicates.add(criteriaBuilder.equal(root.get(attribute).get(subAttribute), value));
        }
        return this;
      }

      public PredicateBuilder<T> range(String attribute, BigDecimal minValue, BigDecimal maxValue) {
        if (minValue != null) {
          predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get(attribute), minValue));
        }
        if (maxValue != null) {
          predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get(attribute), maxValue));
        }
        return this;
      }

      public PredicateBuilder<T> like(String attribute, String value) {
        if (value != null && !value.isBlank()) {
          predicates.add(criteriaBuilder.like(root.get(attribute), "%" + value + "%"));
        }
        return this;
      }

      public PredicateBuilder<T> booleanCondition(String attribute, Boolean isTrueCondition, Boolean isFalseCondition) {
        if (Boolean.TRUE.equals(isTrueCondition) && !Boolean.TRUE.equals(isFalseCondition)) {
          predicates.add(criteriaBuilder.isTrue(root.get(attribute)));
        } else if (Boolean.TRUE.equals(isFalseCondition) && !Boolean.TRUE.equals(isTrueCondition)) {
          predicates.add(criteriaBuilder.isFalse(root.get(attribute)));
        }
        return this;
      }

      public PredicateBuilder<T> inCategories(String attribute, List<Long> incomeCategories,
          List<Long> expenseCategories) {
        if (incomeCategories != null && !incomeCategories.isEmpty() && expenseCategories != null &&
            !expenseCategories.isEmpty()) {
          predicates.add(criteriaBuilder.or(root.get(attribute).get(ID).in(incomeCategories), root
              .get(attribute)
              .get(ID)
              .in(expenseCategories)));
        } else if (incomeCategories != null && !incomeCategories.isEmpty()) {
          predicates.add(root.get(attribute).get(ID).in(incomeCategories));
        } else if (expenseCategories != null && !expenseCategories.isEmpty()) {
          predicates.add(root.get(attribute).get(ID).in(expenseCategories));
        }
        return this;
      }

      public PredicateBuilder<T> dateRange(String attribute, LocalDate from, LocalDate to) {
        if (from != null) {
          predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get(attribute), from));
        }
        if (to != null) {
          predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get(attribute), to));
        }
        return this;
      }

    }

}
