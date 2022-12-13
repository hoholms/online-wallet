package com.endava.wallet.controller;

import com.endava.wallet.entity.Profile;
import com.endava.wallet.entity.Transaction;
import com.endava.wallet.entity.TransactionsCategory;
import com.endava.wallet.entity.User;
import com.endava.wallet.entity.dto.TransactionDto;
import com.endava.wallet.entity.dto.TransactionDtoConverter;
import com.endava.wallet.service.ProfileService;
import com.endava.wallet.service.TransactionService;
import com.endava.wallet.service.TransactionsCategoryService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.util.Pair;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class DashboardController {
    private static final Logger logger = LoggerFactory.getLogger(DashboardController.class);
    private final ProfileService profileService;
    private final TransactionService transactionService;
    private final TransactionsCategoryService categoryService;
    private final TransactionDtoConverter transactionDtoConverter;

    @GetMapping("/dashboard")
    public String dashboard(
            @AuthenticationPrincipal User user,
            Model model
    ) {
        logger.info("Call for dashboard page by user id {}", user.getId());
        Profile currentProfile = profileService.findProfileByUser(user);
        currentProfile.setBalance(profileService.getCalcBalance(currentProfile));

        model.addAttribute("currentProfile", currentProfile);

        model.addAttribute("recentTransactions", currentProfile.getTransactions()
                .stream()
                .sorted(Comparator.comparing(Transaction::getTransactionDate)
                        .thenComparing(Transaction::getId).reversed())
                .toList());

        List<TransactionsCategory> incomeCategories = categoryService.findByIsIncome(true).stream()
                .sorted(Comparator.comparing(TransactionsCategory::getId))
                .toList();
        model.addAttribute("incomeCategories", incomeCategories);

        List<TransactionsCategory> expenseCategories = categoryService.findByIsIncome(false).stream()
                .sorted(Comparator.comparing(TransactionsCategory::getId))
                .toList();
        model.addAttribute("expenseCategories", expenseCategories);

        BigDecimal monthIncome = transactionService.findTranSumDateBetween(
                currentProfile,
                true,
                LocalDate.now().withDayOfMonth(1),
                LocalDate.now()
        );
        model.addAttribute("monthIncome", monthIncome);

        BigDecimal monthExpense = transactionService.findTranSumDateBetween(
                currentProfile,
                false,
                LocalDate.now().withDayOfMonth(1),
                LocalDate.now()
        );
        model.addAttribute("monthExpense", monthExpense);


        Pair<String, BigDecimal> maxIncomeCategory = transactionService.findMaxCategorySumDateBetween(
                currentProfile,
                true,
                LocalDate.now().withDayOfMonth(1),
                LocalDate.now()
        );
        model.addAttribute("maxIncomeCategory", maxIncomeCategory);

        Pair<String, BigDecimal> maxExpenseCategory = transactionService.findMaxCategorySumDateBetween(
                currentProfile,
                false,
                LocalDate.now().withDayOfMonth(1),
                LocalDate.now()
        );
        model.addAttribute("maxExpenseCategory", maxExpenseCategory);

        model.addAttribute("today", LocalDate.now());

        return "dashboard";
    }

    @PostMapping("/dashboard")
    public String addTransaction(
            @AuthenticationPrincipal User user,
            TransactionDto transactionDto
    ) {
        Profile currentProfile = profileService.findProfileByUser(user);
        Transaction transaction = transactionDtoConverter.fromDto(transactionDto, currentProfile);
        transactionService.add(transaction, currentProfile);
//        profileService.calcBalance(user);

        return "redirect:/dashboard";
    }
}
