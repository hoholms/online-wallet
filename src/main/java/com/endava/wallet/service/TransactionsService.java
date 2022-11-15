package com.endava.wallet.service;

import com.endava.wallet.entity.Profile;
import com.endava.wallet.entity.Transaction;
import com.endava.wallet.entity.User;
import com.endava.wallet.repository.TransactionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class TransactionsService {

    private TransactionRepository transactionRepository;
    private ProfileService profileService;

    public List<Transaction> getAllTransactions() {
        return this.transactionRepository.findAll();
    }

    public List<Transaction> findTransactionByProfileOrderByIdAsc(User user, Model model) {
        Profile profile = profileService.findProfileByUser(user);
        return transactionRepository.findTransactionByProfileOrderByIdAsc(profile);
    }

    public Transaction findTransactionById(Long id) {
        return this.transactionRepository.findTransactionById(id);
    }

    public void save(Transaction transaction) {
        transactionRepository.save(transaction);
    }

    public LocalDate parseDate(String transactionDate) {
        return LocalDate.parse(transactionDate);
    }

    public void deleteById(Long id) {

        transactionRepository.deleteById(id);
    }
}
