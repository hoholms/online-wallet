package com.endava.wallet.service;

import com.endava.wallet.entity.TransactionsCategory;
import com.endava.wallet.repository.TransactionsCategoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class TransactionsCategoryService
{
    public final TransactionsCategoryRepository categoryRepository;

    public List<TransactionsCategory> findAllByIsIncomeById(Long id){

        TransactionsCategory category = null;
        if (categoryRepository.findById(id).isPresent()) {
            categoryRepository.findById(id).get();
            return categoryRepository.findAll().stream()
                    .filter(a -> {
                        assert false;
                        return a.getIsIncome().equals(category.getIsIncome());
                    })
                    .toList();
        }
        return null;
    }
    public TransactionsCategory findByCategory(String category){
        return categoryRepository.findByCategory(category);
    }
}
