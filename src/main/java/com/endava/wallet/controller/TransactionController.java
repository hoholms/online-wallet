package com.endava.wallet.controller;

import com.endava.wallet.entity.User;
import com.endava.wallet.service.TransactionsCategoryService;
import com.endava.wallet.service.TransactionsService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@Controller
@RequestMapping("/transactions")
@RequiredArgsConstructor
public class TransactionController {
    private final TransactionsService transactionsService;

    private final TransactionsCategoryService categoryService;

    @GetMapping
    public String transactionsList(@AuthenticationPrincipal User user, Model model) {

        model.addAttribute("transactions", transactionsService.findTransactionByUserIdOrderAsc(user));
        model.addAttribute("userID", user.getId());

        return "transactions";
    }

    @GetMapping("{transactionID}")
    public String transactionEditForm(@PathVariable Long transactionID, Model model) {
        model.addAttribute("transactionEdit",
                transactionsService.findTransactionById(transactionID));

        model.addAttribute("categories",
                categoryService.findAllCategoriesByTransactionIdByIsIncome(transactionID));
        return "transactionEdit";
    }

    @GetMapping("/delete/{transactionID}")
    public String transactionDelete(@PathVariable Long transactionID, @AuthenticationPrincipal User user, Model model) {

        transactionsService.deleteById(transactionID, user, model);

        return "transactions";
    }

    @PostMapping
    public String transactionSave(
            @AuthenticationPrincipal User user,
            @RequestParam Long id,
            @RequestParam(required = false) String message,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) BigDecimal amount,
            @RequestParam String transactionDate
    ) {

        transactionsService.save(user, id, message, category, amount, transactionDate);


        return "redirect:/transactions";
    }

}
