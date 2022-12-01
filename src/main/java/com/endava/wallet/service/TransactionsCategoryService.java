package com.endava.wallet.service;

import com.endava.wallet.entity.Transaction;
import com.endava.wallet.entity.TransactionsCategory;
import com.endava.wallet.entity.dto.TransactionsCategoryDto;
import com.endava.wallet.exception.TransactionCategoryNotFoundException;
import com.endava.wallet.repository.TransactionsCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionsCategoryService {
    private final TransactionsCategoryRepository categoryRepository;

    private final TransactionService transactionService;

    public List<TransactionsCategory> findAllCategoriesByTransactionIdByIsIncome(Long transactionId) {

        Transaction transaction = transactionService.findTransactionById(transactionId);

        TransactionsCategory category = transaction.getCategory();

        return categoryRepository.findAll().stream()
                .filter(a -> a.getIsIncome().equals(category.getIsIncome()))
                .toList();
    }

    public TransactionsCategory findByCategory(String category) {
        return categoryRepository.findByCategory(category)
                .orElseThrow(() -> new TransactionCategoryNotFoundException("Transaction category not found!"));
    }

    public List<TransactionsCategory> findByIsIncome(boolean isIncome) {
        return categoryRepository.findByIsIncome(isIncome);
    }

    public List<TransactionsCategory> findAllCategoriesOrderByIsIncome() {
        return categoryRepository.findAll(Sort.by(Sort.Direction.DESC, "isIncome"));
    }

    public void updateCategory(TransactionsCategoryDto categoryDto) {
        TransactionsCategory categoryFromDB = categoryRepository.findById(categoryDto.getId()).orElseThrow(() -> new TransactionCategoryNotFoundException("Transaction categoryDto not found!"));
        categoryFromDB.setCategory(categoryDto.getCategory());
        if (categoryDto.getIsIncome() != null) {
            categoryFromDB.setIsIncome(categoryDto.getIsIncome());
        } else {
            categoryFromDB.setIsIncome(Boolean.FALSE);
        }
    }

    public void deleteCategoryById(Long id) {
        categoryRepository.deleteById(id);
    }

    public TransactionsCategory findById(Long categoryID) {
        return categoryRepository.findById(categoryID).orElseThrow(() -> new TransactionCategoryNotFoundException("Transaction category not found!"));
    }

    public void addCategory(String category, Boolean isIncome) {
        TransactionsCategory transactionsCategory = TransactionsCategory.builder()
                .category(category)
                .isIncome(isIncome)
                .build();
        categoryRepository.save(transactionsCategory);
    }
}
