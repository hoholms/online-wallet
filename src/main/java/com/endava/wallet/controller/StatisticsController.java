package com.endava.wallet.controller;

import com.endava.wallet.entity.DateWithLabel;
import com.endava.wallet.entity.User;
import com.endava.wallet.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class StatisticsController {
    private final TransactionService transactionService;

    @GetMapping("statistics")
    public String statistics(@AuthenticationPrincipal User user, Model model) {
        List<DateWithLabel> dates = transactionService.findTransactionsDatesWithLabels(user);

        model.addAttribute("dates", dates);

        return "statistics";
    }
}
