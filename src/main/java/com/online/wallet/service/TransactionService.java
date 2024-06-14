package com.online.wallet.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import com.online.wallet.exception.TransactionCategoryNotFoundException;
import com.online.wallet.exception.TransactionNotFoundException;
import com.online.wallet.model.Profile;
import com.online.wallet.model.Transaction;
import com.online.wallet.model.User;
import com.online.wallet.model.dto.CircleStatistics;
import com.online.wallet.model.dto.DateWithLabel;
import com.online.wallet.model.dto.TransactionDto;
import com.online.wallet.repository.TransactionRepository;
import com.online.wallet.repository.TransactionsCategoryRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TransactionService {

  private static final Logger                         logger = LoggerFactory.getLogger(TransactionService.class);
  private final        TransactionRepository          transactionRepository;
  private final        TransactionsCategoryRepository categoryRepository;
  private final        ProfileService                 profileService;

  public void add(Transaction transaction, Profile profile) {
    transactionRepository.save(transaction);
    profileService.save(profile);
    logger.info("Transaction with id: {} was added", transaction.getId());
  }

  public void save(User user, Long id, TransactionDto transactionDto) {
    Profile currentProfile = profileService.findProfileByUser(user);
    Transaction transaction = findTransactionByIdAndProfile(id, currentProfile);

    if (transactionDto.getAmount() != null) {
      transaction.setAmount(transactionDto.getAmount());
    }
    transaction.setCategory(categoryRepository
        .findByCategory(transactionDto.getCategory())
        .orElseThrow(() -> new TransactionCategoryNotFoundException("Transaction category not found!")));
    transaction.setTransactionDate(parseDate(transactionDto.getTransactionDate()));
    transaction.setMessage(transactionDto.getMessage());

    transactionRepository.save(transaction);
    profileService.save(currentProfile);
  }

  public Page<Transaction> findTransactionsByProfile(Profile profile, Pageable pageable) {
    return transactionRepository.findTransactionsByProfile(profile, pageable);
  }

  public Transaction findTransactionByIdAndProfile(Long id, Profile profile) {
    return transactionRepository
        .findTransactionByIdAndProfile(id, profile)
        .orElseThrow(() -> new TransactionNotFoundException("Transaction with id: " + id + " not found"));
  }

  public LocalDate parseDate(String transactionDate) {
    return LocalDate.parse(transactionDate);
  }

  public Transaction findTransactionById(Long id) {
    return transactionRepository
        .findTransactionById(id)
        .orElseThrow(() -> new TransactionNotFoundException("Transaction with id: " + id + " not found"));
  }

  public BigDecimal findTranSumDateBetween(Profile profile, boolean isIncome, LocalDate from, LocalDate to) {
    List<Transaction> transactions = transactionRepository.findByProfileAndIsIncomeAndTransactionDateBetween(profile,
        isIncome, from, to);

    return transactions.stream().map(Transaction::getAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
  }

  public Pair<String, BigDecimal> findMaxCategorySumDateBetween(Profile profile, boolean isIncome, LocalDate from,
      LocalDate to) {
    String maxTranCategory = transactionRepository.findMaxCategoryDateBetween(profile, isIncome, from, to);
    if (maxTranCategory == null) {
      maxTranCategory = "nothing";
    }

    BigDecimal maxTranSum = transactionRepository.findMaxSumDateBetween(profile, isIncome, from, to);
    if (maxTranSum == null) {
      maxTranSum = BigDecimal.ZERO;
    }

    return Pair.of(maxTranCategory, maxTranSum);
  }

  public CircleStatistics findCategoryAndSumByProfileAndIsIncome(Profile profile, Boolean isIncome) {
    return new CircleStatistics(transactionRepository.findCategoryByProfileAndIsIncome(profile, isIncome),
        transactionRepository.findCategorySumByProfileAndIsIncome(profile, isIncome));
  }

  public CircleStatistics findCategoryAndSumByProfileAndIsIncome(Profile profile, Boolean isIncome, DateWithLabel from,
      DateWithLabel to) {
    return new CircleStatistics(transactionRepository.findCategoryByProfileAndIsIncomeDateBetween(profile, isIncome,
        from.getDate(), to.getDate()), transactionRepository.findCategorySumByProfileAndIsIncomeDateBetween(profile,
        isIncome, from.getDate(), to.getDate()));
  }

  public List<DateWithLabel> findTransactionsDatesWithLabels(User user) {
    Profile profile = profileService.findProfileByUser(user);

    List<LocalDate> dates = profile
        .getTransactions()
        .stream()
        .map(transaction -> transaction.getTransactionDate().withDayOfMonth(1))
        .filter(distinctByKeys(LocalDate::getMonth, LocalDate::getYear))
        .sorted(Comparator.naturalOrder())
        .toList();

    return dates.stream().map(DateWithLabel::new).toList();
  }

  private static <T> Predicate<T> distinctByKeys(Function<? super T, ?>... keyExtractors) {

    final Map<List<?>, Boolean> seen = new ConcurrentHashMap<>();

    return t -> {

      final List<?> keys = Arrays.stream(keyExtractors).map(ke -> ke.apply(t)).collect(Collectors.toList());

      return seen.putIfAbsent(keys, Boolean.TRUE) == null;

    };

  }

  public List<DateWithLabel> findTransactionsDatesWithLabels(Profile profile, DateWithLabel from, DateWithLabel to) {
    List<LocalDate> dates = profile
        .getTransactions()
        .stream()
        .map(transaction -> transaction.getTransactionDate().withDayOfMonth(1))
        .filter(distinctByKey(LocalDate::getMonth))
        .filter(date -> (date.isAfter(from.getDate()) || date.isEqual(from.getDate())) &&
            (date.isBefore(to.getDate()) || date.isEqual(to.getDate())))
        .sorted(Comparator.naturalOrder())
        .toList();

    return dates.stream().map(DateWithLabel::new).toList();
  }

  public static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
    Set<Object> seen = ConcurrentHashMap.newKeySet();
    return t -> seen.add(keyExtractor.apply(t));
  }

  public void deleteTransactionById(Long transactionID, User user) {
    Profile currentProfile = profileService.findProfileByUser(user);

    transactionRepository.deleteById(transactionID);

    profileService.save(currentProfile);
  }

}
