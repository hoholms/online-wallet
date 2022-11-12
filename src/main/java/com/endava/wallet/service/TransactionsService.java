package com.endava.wallet.service;

import com.endava.wallet.entity.Transaction;
import com.endava.wallet.repository.TransactionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class TransactionsService {

    private TransactionRepository transactionRepository;

    public List<Transaction> getAllTransactions(){
        return this.transactionRepository.findAll();
    }
}
