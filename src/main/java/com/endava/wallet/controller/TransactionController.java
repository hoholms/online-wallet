package com.endava.wallet.controller;

import com.endava.wallet.entity.Profile;
import com.endava.wallet.entity.Transaction;
import com.endava.wallet.entity.User;
import com.endava.wallet.service.ProfileService;
import com.endava.wallet.service.TransactionService;
import com.endava.wallet.service.TransactionsCategoryService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@Controller
@RequestMapping("/transactions")
@RequiredArgsConstructor
public class TransactionController {
    private static final Logger logger = LoggerFactory.getLogger(TransactionController.class);
    private final TransactionService transactionService;
    private final TransactionsCategoryService categoryService;
    private final ProfileService profileService;

    @GetMapping("{transactionID}")
    public String transactionEditForm(
            @AuthenticationPrincipal User user,
            @PathVariable Long transactionID,
            Model model
    ) {
        logger.info("Call for transaction with id: {} edit page", transactionID);

        Profile currentProfile = profileService.findProfileByUser(user);

        Transaction transaction = transactionService.findTransactionByIdAndProfile(transactionID, currentProfile);

        model.addAttribute("transactionEdit", transaction);
        model.addAttribute("categories",
                categoryService.findAllCategoriesByTransactionIdByIsIncome(transactionID));

        return "transactionEdit";
    }

    @GetMapping("/delete/{transactionID}")
    public String transactionDelete(@AuthenticationPrincipal User user, @PathVariable Long transactionID) {
        transactionService.deleteTransactionById(transactionID, user);
        profileService.calcBalance(user);
        logger.info("Deleted transaction with id: {}", transactionID);

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
        profileService.calcBalance(user);
        logger.info("Saved transaction with id: {}", id);

        return "redirect:/dashboard";
    }
}
