package com.endava.wallet.service;

import com.endava.wallet.entity.TransactionsCategory;
import com.endava.wallet.exception.ApiRequestException;
import com.endava.wallet.repository.TransactionsCategoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class TransactionsCategoryService {
    public final TransactionsCategoryRepository categoryRepository;

    public final TransactionService transactionService;

    public List<TransactionsCategory> findAllCategoriesByTransactionIdByIsIncome(Long transactionId) {

        Long categoryId = transactionService.findTransactionById(transactionId).getCategory().getId();

        Optional<TransactionsCategory> categoryOptional = categoryRepository.findById(id);
        TransactionsCategory category;

        if (categoryRepository.findById(categoryId).isPresent()) {
            category = categoryRepository.findById(categoryId).get();

            return categoryRepository.findAll().stream()
                    .filter(a ->
                         a.getIsIncome().equals(category.getIsIncome())
                    )
                    .toList();
        } else {

            throw new ApiRequestException(
                    "There is no categories similar to this transaction's categories in database");

        }
    }

    public TransactionsCategory findByCategory(String category) {
        return categoryRepository.findByCategory(category);
    }

    public List<TransactionsCategory> findByIsIncome(boolean isIncome) {
        return categoryRepository.findByIsIncome(isIncome);
    }
}
