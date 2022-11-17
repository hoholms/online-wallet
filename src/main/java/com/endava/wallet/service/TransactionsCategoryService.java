package com.endava.wallet.service;

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

        Long id = transactionService.findTransactionById(transactionId).getCategory().getId();

        TransactionsCategory category;
        if (categoryRepository.findById(id).isPresent()) {
            category = categoryRepository.findById(id).get();
            return categoryRepository.findAll().stream()
                    .filter(a -> {
                        assert false;

                        return a.getIsIncome().equals(category.getIsIncome());
                    })
                    .toList();
        } else {
            return null;
        }
    }
    //sdfsdfds

    public TransactionsCategory findByCategory(String category) {
        return categoryRepository.findByCategory(category);
    }

    public List<TransactionsCategory> findByIsIncome(boolean isIncome) {
        return categoryRepository.findByIsIncome(isIncome);
    }
}
