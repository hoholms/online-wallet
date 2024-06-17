package com.online.wallet.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

  private static final Logger             logger = LoggerFactory.getLogger(StatisticsController.class);
  private final        TransactionService transactionService;
  private final        ProfileService     profileService;

  @GetMapping("statistics")
  public String statistics(@AuthenticationPrincipal User user, Model model) {
    logger.info("Call for statistics page by user id {}", user.getId());

    List<DateWithLabel> dates = transactionService.findTransactionsDatesWithLabels(user);
    model.addAttribute("dates", dates);
    logger.debug("Retrieved transaction dates with labels for user id {}: {}", user.getId(), dates);

    model.addAttribute("currentProfile", profileService.findProfileByUser(user));
    logger.debug("Retrieved profile for user id {}", user.getId());

    return "statistics";
  }

}
