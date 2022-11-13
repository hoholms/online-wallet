package com.endava.wallet.controller;

import com.endava.wallet.entity.Transaction;
import com.endava.wallet.repository.TransactionsCategoryRepository;
import com.endava.wallet.service.TransactionsService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@AllArgsConstructor
@RequestMapping("/transactions")
public class TransactionController {
    private TransactionsCategoryRepository transactionsCategoryRepository;
    private TransactionsService transactionsService;

    @GetMapping("/")
    public String getAllTransaction(Model model) {
        model.addAttribute("transactions", transactionsService.getAllTransactions());
        return "transactions";
    }

    @GetMapping("/{transaction}")
    public String transactionEditForm(@PathVariable Transaction transaction, Model model) {
        model.addAttribute("transactionEdit", transaction);
        model.addAttribute("categories", transactionsCategoryRepository.findAll());
        return "transactionEdit";
    }


}
