package com.endava.wallet.service;

import com.endava.wallet.entity.Profile;
import com.endava.wallet.entity.Transaction;
import com.endava.wallet.entity.User;
import com.endava.wallet.repository.TransactionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class TransactionsService {

    private TransactionRepository transactionRepository;

    private TransactionsCategoryService categoryService;

    private ProfileService profileService;


    public List<Transaction> findTransactionByUserIdOrderAsc(User user) {
        Profile profile = profileService.findProfileByUser(user);
        return transactionRepository.findTransactionByProfileOrderByIdAsc(profile);
    }

    public Transaction findTransactionById(Long id) {
        return transactionRepository.findTransactionById(id);
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

        if (amount != null) {
            transaction.setAmount(amount);
        }
        transaction.setCategory(categoryService.findByCategory(category));
        transaction.setTransactionDate(parseDate(transactionDate));
        transaction.setMessage(message);

        transactionRepository.save(transaction);
        profileService.save(profile);
    }

    public LocalDate parseDate(String transactionDate) {
        return LocalDate.parse(transactionDate);
    }

    public void deleteById(Long transactionID, User user, Model model) {
        Transaction transaction = transactionRepository.findTransactionById(transactionID);
        transactionRepository.deleteById(transactionID);
        Profile profile = profileService.findProfileByUser(user);
        if (Boolean.TRUE.equals(transaction.getIsIncome())) {
            profile.setBalance(profile.getBalance().subtract(transaction.getAmount()));
        } else {
            profile.setBalance(profile.getBalance().add(transaction.getAmount()));
        }
        profileService.save(profile);
        model.addAttribute("transactions", findTransactionByUserIdOrderAsc(user));
    }
}
