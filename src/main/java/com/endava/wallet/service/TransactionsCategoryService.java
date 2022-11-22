package com.endava.wallet.service;

import com.endava.wallet.entity.Transaction;
import com.endava.wallet.entity.TransactionsCategory;
import com.endava.wallet.repository.TransactionsCategoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class TransactionsCategoryService {
    public final TransactionsCategoryRepository categoryRepository;

    public final TransactionService transactionService;

    public List<TransactionsCategory> findAllCategoriesByTransactionIdByIsIncome(Long transactionId) {

        Transaction transaction =  transactionService.findTransactionById(transactionId);

        TransactionsCategory category = transaction.getCategory();

        return categoryRepository.findAll().stream()
                    .filter(a -> a.getIsIncome().equals(category.getIsIncome()))
                    .toList();
    }

    public TransactionsCategory findByCategory(String category) {
        return categoryRepository.findByCategory(category);
    }

    public List<TransactionsCategory> findByIsIncome(boolean isIncome) {
        return categoryRepository.findByIsIncome(isIncome);
    }
}
