package com.endava.wallet.controller;

import com.endava.wallet.entity.Transaction;
import com.endava.wallet.entity.TransactionsCategory;
import com.endava.wallet.entity.User;
import com.endava.wallet.exception.ApiRequestException;
import com.endava.wallet.service.TransactionService;
import com.endava.wallet.service.TransactionsCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@Controller
@RequestMapping("/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;
    private final TransactionsCategoryService categoryService;

    @GetMapping("{transactionID}")
    public String transactionEditForm(@PathVariable Long transactionID, Model model) {

        Transaction transactionEdit = transactionService.findTransactionById(transactionID);
        if (transactionEdit == null) {
            throw new ApiRequestException(
                    "There is no transaction with id = " + transactionID + " in database");
        }
        model.addAttribute("transactionEdit", transactionEdit);

        List<TransactionsCategory> similarCategories =
                categoryService.findAllCategoriesByTransactionIdByIsIncome(transactionID);
        if (similarCategories.isEmpty()) {
            throw new ApiRequestException(
                    "There is no categories similar to this transaction's categories in database");
        }
        model.addAttribute("categories", similarCategories);

        return "transactionEdit";
    }

    @GetMapping("/delete/{transactionID}")
    public String transactionDelete(@PathVariable Long transactionID, @AuthenticationPrincipal User user, Model model) {

        Transaction transaction = transactionService.findTransactionById(transactionID);
        if (transaction == null) {
            throw new ApiRequestException(
                    "There is no transaction with id = " + transactionID + " in database");
        }

        transactionService.deleteTransaction(transaction, user);

        List<Transaction> transactionList = transactionService.findRecentTransactionsByUser(user);
        model.addAttribute("transactions", transactionList);

        return "redirect:/dashboard";
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


        return "redirect:/dashboard";
    }

}
