package com.endava.wallet.controller;

import com.endava.wallet.entity.DateWithLabel;
import com.endava.wallet.entity.Profile;
import com.endava.wallet.entity.User;
import com.endava.wallet.service.ProfileService;
import com.endava.wallet.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class StatisticsController {
    private final TransactionService transactionService;
    private final ProfileService profileService;

    @GetMapping("statistics")
    public String statistics(@AuthenticationPrincipal User user, Model model) {
        Profile currentProfile = profileService.findProfileByUser(user);

        List<DateWithLabel> dates = transactionService.findTransactionsDatesWithLabels(currentProfile);

        model.addAttribute("dates", dates);

        return "statistics";
    }
}
