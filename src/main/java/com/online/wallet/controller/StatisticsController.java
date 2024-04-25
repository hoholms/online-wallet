package com.online.wallet.controller;

import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.online.wallet.model.User;
import com.online.wallet.model.dto.DateWithLabel;
import com.online.wallet.service.ProfileService;
import com.online.wallet.service.TransactionService;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class StatisticsController {

  private final TransactionService transactionService;
  private final ProfileService     profileService;

  @GetMapping("statistics")
  public String statistics(@AuthenticationPrincipal User user, Model model) {
    List<DateWithLabel> dates = transactionService.findTransactionsDatesWithLabels(user);
    model.addAttribute("dates", dates);

    model.addAttribute("currentProfile", profileService.findProfileByUser(user));

    return "statistics";
  }

}
