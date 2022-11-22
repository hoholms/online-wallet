package com.endava.wallet.controller;

import com.endava.wallet.service.ProfileService;
import com.endava.wallet.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class StatisticsController {
    private final TransactionService transactionService;
    private final ProfileService profileService;

    @GetMapping("statistics")
    public String statistics() {
        return "statistics";
    }
}
