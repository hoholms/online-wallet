package com.endava.wallet.controller;

import com.endava.wallet.entity.*;
import com.endava.wallet.repository.TransactionRepository;
import com.endava.wallet.service.ProfileService;
import com.endava.wallet.service.TransactionService;
import com.endava.wallet.service.TransactionsCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.util.Pair;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class DashboardController {
    private final ProfileService profileService;
    private final TransactionService transactionService;
    private final TransactionsCategoryService categoryService;
    private final TransactionDtoConverter transactionDtoConverter;

    @GetMapping("/dashboard")
    public String dashboard(
            @AuthenticationPrincipal User user,
            Model model
    ) {
        Profile currentProfile = profileService.findProfileByUser(user);
        model.addAttribute("currentProfile", currentProfile);

        model.addAttribute("recentTransactions", transactionService.findRecentTransactions(currentProfile));

        List<TransactionsCategory> incomeCategories = categoryService.findByIsIncome(true);
        model.addAttribute("incomeCategories", incomeCategories);

        List<TransactionsCategory> expenseCategories = categoryService.findByIsIncome(false);
        model.addAttribute("expenseCategories", expenseCategories);

        BigDecimal monthIncome = transactionService.findTranSumDateBetween(
                currentProfile,
                true,
                LocalDate.now().withDayOfMonth(1),
                LocalDate.now().withDayOfMonth(LocalDate.now().getMonth().length(LocalDate.now().isLeapYear()))
        );
        model.addAttribute("monthIncome", monthIncome);

        BigDecimal monthExpense = transactionService.findTranSumDateBetween(
                currentProfile,
                false,
                LocalDate.now().withDayOfMonth(1),
                LocalDate.now().withDayOfMonth(LocalDate.now().getMonth().length(LocalDate.now().isLeapYear()))
        );
        model.addAttribute("monthExpense", monthExpense);


        Pair<String, BigDecimal> maxIncomeCategory = transactionService.findMaxCategorySumDateBetween(
                currentProfile,
                true,
                LocalDate.now().withDayOfMonth(1),
                LocalDate.now().withDayOfMonth(LocalDate.now().getMonth().length(LocalDate.now().isLeapYear()))
        );
        model.addAttribute("maxIncomeCategory", maxIncomeCategory);

        Pair<String, BigDecimal> maxExpenseCategory = transactionService.findMaxCategorySumDateBetween(
                currentProfile,
                false,
                LocalDate.now().withDayOfMonth(1),
                LocalDate.now().withDayOfMonth(LocalDate.now().getMonth().length(LocalDate.now().isLeapYear()))
        );
        model.addAttribute("maxExpenseCategory", maxExpenseCategory);

        model.addAttribute("today", LocalDate.now());

        return "dashboard";
    }

    @PostMapping("/dashboard")
    public String addTransaction(
            @AuthenticationPrincipal User user,
            TransactionDto transactionDto,
            Model model
    ) {
        Profile currentProfile = profileService.findProfileByUser(user);

        Transaction transaction = transactionDtoConverter.fromDto(transactionDto, currentProfile);

        transactionService.save(transaction);

        if (Boolean.TRUE.equals(transactionDto.getIsIncome())) {
            currentProfile.setBalance(currentProfile.getBalance().add(transactionDto.getAmount()));
        } else {
            currentProfile.setBalance(currentProfile.getBalance().subtract(transactionDto.getAmount()));
        }
        profileService.save(currentProfile);

        return dashboard(user, model);
    }
}
