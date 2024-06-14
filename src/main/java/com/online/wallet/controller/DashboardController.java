package com.online.wallet.controller;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.util.Pair;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.online.wallet.model.Profile;
import com.online.wallet.model.Transaction;
import com.online.wallet.model.TransactionsCategory;
import com.online.wallet.model.User;
import com.online.wallet.model.dto.TransactionDto;
import com.online.wallet.model.dto.TransactionDtoConverter;
import com.online.wallet.service.ProfileService;
import com.online.wallet.service.TransactionService;
import com.online.wallet.service.TransactionsCategoryService;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class DashboardController {

  private static final Logger                      logger = LoggerFactory.getLogger(DashboardController.class);
  private final        ProfileService              profileService;
  private final        TransactionService          transactionService;
  private final        TransactionsCategoryService categoryService;
  private final        TransactionDtoConverter     transactionDtoConverter;

  @GetMapping("/dashboard")
  public String dashboard(@AuthenticationPrincipal User user, Model model,
                          @PageableDefault(sort = {"transactionDate", "id"}, direction = Sort.Direction.DESC)
                          Pageable pageable) {
    logger.info("Call for dashboard page by user id {}", user.getId());
    Profile currentProfile = profileService.findProfileByUser(user);
    currentProfile.setBalance(profileService.getCalcBalance(currentProfile));

    setModel(model, currentProfile, pageable);

    return "dashboard";
  }

  private void setModel(Model model, Profile currentProfile, Pageable pageable) {
    model.addAttribute("currentProfile", currentProfile);

    model.addAttribute("recentTransactions", transactionService.findTransactionsByProfile(currentProfile, pageable));

    List<TransactionsCategory> incomeCategories = categoryService
        .findByIsIncome(true)
        .stream()
        .sorted(Comparator.comparing(TransactionsCategory::getId))
        .toList();
    model.addAttribute("incomeCategories", incomeCategories);

    List<TransactionsCategory> expenseCategories = categoryService
        .findByIsIncome(false)
        .stream()
        .sorted(Comparator.comparing(TransactionsCategory::getId))
        .toList();
    model.addAttribute("expenseCategories", expenseCategories);

    BigDecimal monthIncome = transactionService.findTranSumDateBetween(currentProfile, true, LocalDate
        .now()
        .withDayOfMonth(1), LocalDate.now());
    model.addAttribute("monthIncome", monthIncome);

    BigDecimal monthExpense = transactionService.findTranSumDateBetween(currentProfile, false, LocalDate
        .now()
        .withDayOfMonth(1), LocalDate.now());
    model.addAttribute("monthExpense", monthExpense);


    Pair<String, BigDecimal> maxIncomeCategory = transactionService.findMaxCategorySumDateBetween(currentProfile,
        true, LocalDate
        .now()
        .withDayOfMonth(1), LocalDate.now());
    model.addAttribute("maxIncomeCategory", maxIncomeCategory);

    Pair<String, BigDecimal> maxExpenseCategory = transactionService.findMaxCategorySumDateBetween(currentProfile,
        false, LocalDate
        .now()
        .withDayOfMonth(1), LocalDate.now());
    model.addAttribute("maxExpenseCategory", maxExpenseCategory);

    model.addAttribute("today", LocalDate.now());
  }

  @PostMapping("/dashboard")
  public String addTransaction(@AuthenticationPrincipal User user, @Valid TransactionDto transactionDto,
      BindingResult bindingResult, Model model, @PageableDefault(sort = {"transactionDate", "id"}, direction = Sort.Direction.DESC)
                                 Pageable pageable) {
    Profile currentProfile = profileService.findProfileByUser(user);

    if (bindingResult.hasErrors()) {
      Map<String, String> errorsMap = ControllerUtils.getErrors(bindingResult);
      model.addAttribute("errorsMap", errorsMap);
      model.addAttribute("transactionDto", transactionDto);
    } else {
      Transaction transaction = transactionDtoConverter.fromDto(transactionDto, currentProfile);
      transactionService.add(transaction, currentProfile);
      currentProfile.setBalance(profileService.getCalcBalance(currentProfile));
    }

    setModel(model, currentProfile, pageable);

    profileService.calcBalance(user);

    return "dashboard";
  }

}
