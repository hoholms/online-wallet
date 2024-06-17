package com.online.wallet.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.online.wallet.exception.TransactionCategoryNotFoundException;
import com.online.wallet.model.Transaction;
import com.online.wallet.model.TransactionsCategory;
import com.online.wallet.model.dto.TransactionsCategoryDto;
import com.online.wallet.model.dto.TransactionsCategoryDtoConverter;
import com.online.wallet.repository.TransactionsCategoryRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TransactionsCategoryService {

  private static final Logger                           logger =
      LoggerFactory.getLogger(TransactionsCategoryService.class);
  private final        TransactionsCategoryRepository   categoryRepository;
  private final        TransactionService               transactionService;
  private final        TransactionsCategoryDtoConverter categoryDtoConverter;

  public List<TransactionsCategory> findAllCategoriesByTransactionIdByIsIncome(Long transactionId) {
    logger.info("Fetching categories by transaction id {}", transactionId);
    Transaction transaction = transactionService.findTransactionById(transactionId);
    TransactionsCategory category = transaction.getCategory();

    List<TransactionsCategory> categories = categoryRepository
        .findAll()
        .stream()
        .filter(a -> a.getIsIncome().equals(category.getIsIncome()))
        .toList();
    logger.debug("Found {} categories for transaction id {}", categories.size(), transactionId);
    return categories;
  }

  public TransactionsCategory findByCategory(String category) {
    logger.info("Fetching category by name {}", category);
    return categoryRepository.findByCategory(category).orElseThrow(() -> {
      logger.error("Transaction category not found: {}", category);
      return new TransactionCategoryNotFoundException("Transaction category not found!");
    });
  }

  public List<TransactionsCategory> findByIsIncome(boolean isIncome) {
    logger.info("Fetching categories by isIncome {}", isIncome);
    return categoryRepository.findByIsIncome(isIncome);
  }

  public List<TransactionsCategory> findAllCategoriesOrderByIsIncome() {
    logger.info("Fetching all categories ordered by isIncome");
    return categoryRepository.findAll(Sort.by(Sort.Direction.DESC, "isIncome"));
  }

  public void updateCategory(TransactionsCategoryDto categoryDto) {
    logger.info("Updating category id {}", categoryDto.getId());
    TransactionsCategory categoryFromDB = categoryRepository.findById(categoryDto.getId()).orElseThrow(() -> {
      logger.error("Transaction category not found for id {}", categoryDto.getId());
      return new TransactionCategoryNotFoundException("Transaction categoryDto not found!");
    });
    categoryFromDB.setCategory(categoryDto.getCategory());
    categoryFromDB.setIsIncome(categoryDto.getIsIncome() != null ? categoryDto.getIsIncome() : Boolean.FALSE);
    categoryRepository.save(categoryFromDB);
    logger.info("Updated category id {}", categoryDto.getId());
  }

  public void deleteCategoryById(Long id) {
    logger.info("Deleting category id {}", id);
    categoryRepository.deleteById(id);
    logger.info("Deleted category id {}", id);
  }

  public TransactionsCategory findById(Long categoryID) {
    logger.info("Fetching category by id {}", categoryID);
    return categoryRepository.findById(categoryID).orElseThrow(() -> {
      logger.error("Transaction category not found for id {}", categoryID);
      return new TransactionCategoryNotFoundException("Transaction category not found!");
    });
  }

  public void addCategory(String category, Boolean isIncome) {
    logger.info("Adding new category with name {}", category);
    TransactionsCategory transactionsCategory = TransactionsCategory
        .builder()
        .category(category)
        .isIncome(isIncome)
        .build();
    categoryRepository.save(transactionsCategory);
    logger.info("Added new category with name {}", category);
  }

  public void addCategory(TransactionsCategoryDto categoryDto) {
    logger.info("Adding new category from DTO {}", categoryDto);
    TransactionsCategory transactionsCategory = categoryDtoConverter.fromDto(categoryDto);
    categoryRepository.save(transactionsCategory);
    logger.info("Added new category from DTO {}", categoryDto);
  }

}
