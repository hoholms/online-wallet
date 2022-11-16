package com.endava.wallet.controller;

import com.endava.wallet.entity.User;
import com.endava.wallet.service.TransactionService;
import com.endava.wallet.service.TransactionsCategoryService;
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
    private final String TRANSACTION_VIEW = "transactions";
    private final TransactionService transactionService;

    private final TransactionsCategoryService categoryService;

    @GetMapping
    public String transactionsList(@AuthenticationPrincipal User user, Model model) {

        model.addAttribute(TRANSACTION_VIEW, transactionService.findTransactionByUserIdOrderAsc(user));
        model.addAttribute("userID", user.getId());

        return TRANSACTION_VIEW;
    }

    @GetMapping("{transactionID}")
    public String transactionEditForm(@PathVariable Long transactionID, Model model) {
        model.addAttribute("transactionEdit",
                transactionService.findTransactionById(transactionID));

        model.addAttribute("categories",
                categoryService.findAllCategoriesByTransactionIdByIsIncome(transactionID));
        return "transactionEdit";
    }

    @GetMapping("/delete/{transactionID}")
    public String transactionDelete(@PathVariable Long transactionID, @AuthenticationPrincipal User user, Model model) {

        transactionService.deleteById(transactionID, user);
        model.addAttribute("transactions", transactionService.findTransactionByUserIdOrderAsc(user));

        return TRANSACTION_VIEW;
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

        transactionService.save(user, id, message, category, amount, transactionDate);


        return "redirect:/transactions";
    }

}
