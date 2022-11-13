package com.endava.wallet.controller;

import com.endava.wallet.entity.Authority;
import com.endava.wallet.entity.Transaction;
import com.endava.wallet.entity.TransactionsCategory;
import com.endava.wallet.entity.User;
import com.endava.wallet.repository.TransactionsCategoryRepository;
import com.endava.wallet.service.TransactionsService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@AllArgsConstructor
public class TransactionsController {
    private TransactionsCategoryRepository transactionsCategoryRepository;
    private TransactionsService transactionsService;

    @GetMapping("/transactions")
    public String getAllTransaction(Model model) {
        model.addAttribute("transactions", transactionsService.getAllTransactions());
        return "transactions";
    }

    @GetMapping("{transaction}")
    public String transactionEditForm(@PathVariable Transaction transaction, Model model) {
        model.addAttribute("transaction", transaction);
        model.addAttribute("categories", transactionsCategoryRepository.findAll());
        return "userEdit";
    }


}
