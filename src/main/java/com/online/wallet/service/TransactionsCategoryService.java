package com.online.wallet.service;

import com.online.wallet.exception.TransactionCategoryNotFoundException;
import com.online.wallet.model.Transaction;
import com.online.wallet.model.TransactionsCategory;
import com.online.wallet.model.dto.TransactionsCategoryDto;
import com.online.wallet.model.dto.TransactionsCategoryDtoConverter;
import com.online.wallet.repository.TransactionsCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionsCategoryService {
    private final TransactionsCategoryRepository categoryRepository;

    private final TransactionService transactionService;

    private final TransactionsCategoryDtoConverter categoryDtoConverter;

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

    public void addCategory(TransactionsCategoryDto categoryDto) {
        TransactionsCategory transactionsCategory = categoryDtoConverter.fromDto(categoryDto);
        categoryRepository.save(transactionsCategory);
    }
}
