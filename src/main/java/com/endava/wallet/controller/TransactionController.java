package com.endava.wallet.controller;

import com.endava.wallet.entity.Profile;
import com.endava.wallet.entity.Transaction;
import com.endava.wallet.entity.User;
import com.endava.wallet.service.ProfileService;
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
    private final ProfileService profileService;

    @GetMapping("{transactionID}")
    public String transactionEditForm(@AuthenticationPrincipal User user, @PathVariable Long transactionID, Model model) {
        Profile currentProfile = profileService.findProfileByUser(user);
        Transaction transaction = transactionService.findTransactionById(transactionID);
        if (currentProfile.getId() != transaction.getProfile().getId()) {
            return "redirect:/dashboard";
        }

        model.addAttribute("transactionEdit", transaction);

        model.addAttribute("categories",
                categoryService.findAllCategoriesByTransactionIdByIsIncome(transactionID));

        return "transactionEdit";
    }

    @GetMapping("/delete/{transactionID}")
    public String transactionDelete(@PathVariable Long transactionID, @AuthenticationPrincipal User user, Model model) {

        transactionService.deleteTransactionById(transactionID, user);

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
