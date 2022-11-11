package com.endava.wallet.controller;

import com.endava.wallet.entity.*;
import com.endava.wallet.repository.ExpenseTransferRepository;
import com.endava.wallet.repository.IncomeTransferRepository;
import com.endava.wallet.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
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
    private final IncomeTransferRepository incomeTransferRepository;
    private final ExpenseTransferRepository expenseTransferRepository;
    private static final String FRONT_ENDPOINT = "dashboard";

    @GetMapping("/dashboard")
    public String dashboard(@AuthenticationPrincipal User user, Model model) {
        Profile currentProfile = profileRepository.findByUser(user);
        List<IncomeTransfer> todayIncomeTransfers = incomeTransferRepository.findIncomeTransferByProfileAndTransferDateBetween(currentProfile, LocalDate.now(), LocalDate.now());
        List<ExpenseTransfer> todayExpenseTransfers = expenseTransferRepository.findExpenseTransferByProfileAndTransferDateBetween(currentProfile, LocalDate.now(), LocalDate.now());
        model.addAttribute(todayIncomeTransfers);
        model.addAttribute(todayExpenseTransfers);

        return FRONT_ENDPOINT;
    }

    @PostMapping("/dashboard")
    public String addIncome(
            @AuthenticationPrincipal User user,
            @RequestParam IncomeCategory incCategory,
            @RequestParam BigDecimal incAmount,
            @RequestParam String incMessage,
            @RequestParam LocalDate incTransferDate
    ) {
        IncomeTransfer incomeTransfer = IncomeTransfer.builder()
                .profile(profileRepository.findByUser(user))
                .category(incCategory)
                .amount(incAmount)
                .message(incMessage)
                .transferDate(incTransferDate)
                .build();

        incomeTransferRepository.save(incomeTransfer);

        return FRONT_ENDPOINT;
    }

    @PostMapping("/dashboardd")
    public String addExpense(
            @AuthenticationPrincipal User user,
            @RequestParam ExpenseCategory expCategory,
            @RequestParam BigDecimal expAmount,
            @RequestParam String expMessage,
            @RequestParam LocalDate expTransferDate
    ) {
        ExpenseTransfer expenseTransfer = ExpenseTransfer.builder()
                .profile(profileRepository.findByUser(user))
                .category(expCategory)
                .amount(expAmount)
                .message(expMessage)
                .transferDate(expTransferDate)
                .build();

        expenseTransferRepository.save(expenseTransfer);

        return FRONT_ENDPOINT;
    }
}
