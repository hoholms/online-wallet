package com.endava.wallet.controller;

import com.endava.wallet.entity.Profile;
import com.endava.wallet.entity.Transaction;
import com.endava.wallet.entity.TransactionsCategory;
import com.endava.wallet.entity.User;
import com.endava.wallet.repository.ProfileRepository;
import com.endava.wallet.repository.TransactionRepository;
import com.endava.wallet.repository.TransactionsCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class DashboardController {
    private final ProfileRepository profileRepository;
    private final TransactionRepository transactionRepository;
    private final TransactionsCategoryRepository categoryRepository;

    @GetMapping("/dashboard")
    public String dashboard(
            @AuthenticationPrincipal User user,
            Model model
    ) {
        Profile currentProfile = profileRepository.findProfileByUser(user);
        model.addAttribute("currentProfile", currentProfile);

        List<Transaction> recentTransactions = transactionRepository.findTop9ByProfileOrderByTransactionDateAsc(currentProfile);
        Collections.reverse(recentTransactions);
        model.addAttribute("recentTransactions", recentTransactions);

        List<TransactionsCategory> incomeCategories = categoryRepository.findByIsIncome(true);
        model.addAttribute("incomeCategories", incomeCategories);

        List<TransactionsCategory> expenseCategories = categoryRepository.findByIsIncome(false);
        model.addAttribute("expenseCategories", expenseCategories);

        List<Transaction> monthIncomeList = transactionRepository.findByProfileAndIsIncomeAndTransactionDateBetween(
                currentProfile,
                true,
                LocalDate.now().withDayOfMonth(1),
                LocalDate.now().withDayOfMonth(LocalDate.now().getMonth().length(LocalDate.now().isLeapYear())));

        BigDecimal monthIncome = monthIncomeList.stream()
                .map(Transaction::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        model.addAttribute("monthIncome", monthIncome);

        List<Transaction> monthExpenseList = transactionRepository.findByProfileAndIsIncomeAndTransactionDateBetween(
                currentProfile,
                false,
                LocalDate.now().withDayOfMonth(1),
                LocalDate.now().withDayOfMonth(LocalDate.now().getMonth().length(LocalDate.now().isLeapYear())));

        BigDecimal monthExpense = monthExpenseList.stream()
                .map(Transaction::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        model.addAttribute("monthExpense", monthExpense);

        String maxIncomeCategory = transactionRepository.FindMaxCategoryDateBetween(
                currentProfile,
                true,
                LocalDate.now().withDayOfMonth(1),
                LocalDate.now().withDayOfMonth(LocalDate.now().getMonth().length(LocalDate.now().isLeapYear())));
        model.addAttribute("maxIncomeCategory", maxIncomeCategory);

        BigDecimal maxIncomeSum = transactionRepository.FindMaxSumDateBetween(
                currentProfile,
                true,
                LocalDate.now().withDayOfMonth(1),
                LocalDate.now().withDayOfMonth(LocalDate.now().getMonth().length(LocalDate.now().isLeapYear())));
        model.addAttribute("maxIncomeSum", maxIncomeSum);

        String maxExpenseCategory = transactionRepository.FindMaxCategoryDateBetween(
                currentProfile,
                false,
                LocalDate.now().withDayOfMonth(1),
                LocalDate.now().withDayOfMonth(LocalDate.now().getMonth().length(LocalDate.now().isLeapYear())));
        model.addAttribute("maxExpenseCategory", maxExpenseCategory);

        BigDecimal maxExpenseSum = transactionRepository.FindMaxSumDateBetween(
                currentProfile,
                false,
                LocalDate.now().withDayOfMonth(1),
                LocalDate.now().withDayOfMonth(LocalDate.now().getMonth().length(LocalDate.now().isLeapYear())));
        model.addAttribute("maxExpenseSum", maxExpenseSum);

        model.addAttribute("today", LocalDate.now());

        return "dashboard";
    }

    @PostMapping("/dashboard")
    public String addTransaction(
            @AuthenticationPrincipal User user,
            @RequestParam String category,
            @RequestParam boolean isIncome,
            @RequestParam BigDecimal amount,
            @RequestParam String message,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate transactionDate,
            Model model
    ) {
        Profile currentProfile = profileRepository.findProfileByUser(user);
        Transaction transaction = Transaction.builder()
                .profile(currentProfile)
                .category(categoryRepository.findByCategory(category))
                .isIncome(isIncome)
                .amount(amount)
                .message(message)
                .transactionDate(transactionDate)
                .build();

        transactionRepository.save(transaction);

        if (Boolean.TRUE.equals(transaction.getIsIncome())) {
            currentProfile.setBalance(currentProfile.getBalance().add(transaction.getAmount()));
        } else {
            currentProfile.setBalance(currentProfile.getBalance().subtract(transaction.getAmount()));
        }
        profileRepository.save(currentProfile);

        return dashboard(user, model);
    }
}
