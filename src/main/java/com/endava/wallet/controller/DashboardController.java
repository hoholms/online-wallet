package com.endava.wallet.controller;

import com.endava.wallet.entity.Profile;
import com.endava.wallet.entity.Transaction;
import com.endava.wallet.entity.TransactionsCategory;
import com.endava.wallet.entity.User;
import com.endava.wallet.repository.ProfileRepository;
import com.endava.wallet.repository.TransactionRepository;
import com.endava.wallet.repository.TransactionsCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class DashboardController {
    private final ProfileRepository profileRepository;
    private final TransactionRepository transactionRepository;
    private final TransactionsCategoryRepository categoryRepository;

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        List<TransactionsCategory> incomeCategories = categoryRepository.findByIsIncome(true);
        model.addAttribute("incomeCategories", incomeCategories);

        List<TransactionsCategory> expenseCategories = categoryRepository.findByIsIncome(false);
        model.addAttribute("expenseCategories", expenseCategories);

        List<Transaction> transactions = transactionRepository.findAll(Sort.by(Sort.Direction.DESC, "transactionDate"));
        model.addAttribute("transactions", transactions);

        return "dashboard";
    }

    @PostMapping("/dashboard")
    public String addTransaction(
            @AuthenticationPrincipal User user,
            @RequestParam TransactionsCategory category,
            @RequestParam boolean isIncome,
            @RequestParam BigDecimal amount,
            @RequestParam String message,
            @RequestParam LocalDate transactionDate,
            Model model
    ) {
        Profile currentProfile = profileRepository.findByUser(user);
        Transaction transaction = Transaction.builder()
                .profile(currentProfile)
                .category(category)
                .isIncome(isIncome)
                .amount(amount)
                .message(message)
                .transactionDate(transactionDate)
                .build();

        transactionRepository.save(transaction);

        List<Transaction> transactions = transactionRepository.findAll(Sort.by(Sort.Direction.DESC, "transactionDate"));
        model.addAttribute("transactions", transactions);

        return "dashboard";
    }
}
