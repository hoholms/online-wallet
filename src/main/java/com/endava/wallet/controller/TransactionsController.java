package com.endava.wallet.controller;

import com.endava.wallet.service.TransactionsService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@AllArgsConstructor
public class TransactionsController {
    private TransactionsService transactionsService;

    @GetMapping("/transactions")
    public String getAllTransaction(Model model) {
        model.addAttribute("transactions", transactionsService.getAllTransactions());
        return "transactions";
    }


}
