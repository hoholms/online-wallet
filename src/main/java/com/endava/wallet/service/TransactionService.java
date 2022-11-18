package com.endava.wallet.service;

import com.endava.wallet.entity.Profile;
import com.endava.wallet.entity.Transaction;
import com.endava.wallet.entity.User;
import com.endava.wallet.exception.ApiRequestException;
import com.endava.wallet.repository.TransactionRepository;
import com.endava.wallet.repository.TransactionsCategoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class TransactionService {

    private TransactionRepository transactionRepository;
    private TransactionsCategoryRepository categoryRepository;
    private ProfileService profileService;


    public List<Transaction> findRecentTransactionsByUser(User user) {
        Profile profile = profileService.findProfileByUser(user);
        return transactionRepository.findTransactionByProfileOrderByIdAsc(profile);
    }

    public List<Transaction> findRecentTransactionsByProfile(Profile profile) {
        return transactionRepository.findTransactionByProfileOrderByIdAsc(profile);
    }

    public List<Transaction> findByIsIncomeDateBetween(Profile profile, boolean isIncome, LocalDate from, LocalDate to) {

        return transactionRepository.findByProfileAndIsIncomeAndTransactionDateBetween(
                profile,
                isIncome,
                from,
                to);
    }

    public BigDecimal findTranSumDateBetween(Profile profile, boolean isIncome, LocalDate from, LocalDate to) {

        List<Transaction> transactions = transactionRepository.findByProfileAndIsIncomeAndTransactionDateBetween(
                profile,
                isIncome,
                from,
                to);

        return transactions.stream()
                .map(Transaction::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public Pair<String, BigDecimal> findMaxCategorySumDateBetween(Profile profile, boolean isIncome, LocalDate from, LocalDate to) {

        String maxTranCategory = transactionRepository.FindMaxCategoryDateBetween(
                profile,
                isIncome,
                from,
                to
        );
        if (maxTranCategory == null) maxTranCategory = "nothing";

        BigDecimal maxTranSum = transactionRepository.FindMaxSumDateBetween(
                profile,
                isIncome,
                from,
                to
        );
        if (maxTranSum == null) maxTranSum = BigDecimal.ZERO;

        return Pair.of(maxTranCategory, maxTranSum);
    }

    public Transaction findTransactionById(Long id) {
        if(transactionRepository.findTransactionById(id) == null){
            throw new ApiRequestException("Transaction with id " + id + " not found");
        }
        return transactionRepository.findTransactionById(id);
    }

    public void save(Transaction transaction) {
        transactionRepository.save(transaction);
    }

    public void save(User user, Long id, String message, String category, BigDecimal amount, String transactionDate) {

        Profile profile = profileService.findProfileByUser(user);

        Transaction transaction = findTransactionById(id);

        if (amount != null && !amount.equals(transaction.getAmount())) {
            if (Boolean.TRUE.equals(transaction.getIsIncome())) {
                profile.setBalance(profile.getBalance().subtract(transaction.getAmount()));
                profile.setBalance(profile.getBalance().add(amount));
            } else {
                profile.setBalance(profile.getBalance().add(transaction.getAmount()));
                profile.setBalance(profile.getBalance().subtract(amount));
            }
        }

        transaction.setAmount(amount);

        transaction.setCategory(categoryRepository.findByCategory(category));
        transaction.setTransactionDate(parseDate(transactionDate));
        transaction.setMessage(message);

        transactionRepository.save(transaction);
        profileService.save(profile);
    }

    public LocalDate parseDate(String transactionDate) {
        return LocalDate.parse(transactionDate);
    }

    public void deleteTransaction(Transaction transaction, User user) {
        findTransactionById(transaction.getId());

        transactionRepository.delete(transaction);

        Profile profile = profileService.findProfileByUser(user);

        if (Boolean.TRUE.equals(transaction.getIsIncome())) {
            profile.setBalance(profile.getBalance().subtract(transaction.getAmount()));
        } else {
            profile.setBalance(profile.getBalance().add(transaction.getAmount()));
        }

        profileService.save(profile);

    }
}
