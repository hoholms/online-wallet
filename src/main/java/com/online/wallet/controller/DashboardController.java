package com.online.wallet.controller;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.util.Pair;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.online.wallet.model.Profile;
import com.online.wallet.model.Transaction;
import com.online.wallet.model.TransactionsCategory;
import com.online.wallet.model.User;
import com.online.wallet.model.dto.TransactionDto;
import com.online.wallet.model.dto.TransactionDtoConverter;
import com.online.wallet.model.dto.TransactionFilterDTO;
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
      @ModelAttribute TransactionFilterDTO transactionFilterDTO,
      @PageableDefault(sort = {"transactionDate", "id"}, direction = Sort.Direction.DESC) Pageable pageable) {
    logger.info("Dashboard page requested by user id {}", user.getId());

    Profile currentProfile = profileService.findProfileByUser(user);
    if (currentProfile == null) {
      logger.error("No profile found for user id {}", user.getId());
      return "error";
    }

    currentProfile.setBalance(profileService.getCalcBalance(currentProfile));
    logger.debug("Calculated balance for user id {}: {}", user.getId(), currentProfile.getBalance());

    setModel(model, currentProfile, transactionFilterDTO, pageable);

    return "dashboard";
  }

  @PostMapping("/dashboard")
  public String addTransaction(@AuthenticationPrincipal User user, @Valid TransactionDto transactionDto,
      BindingResult bindingResult, Model model,
      @PageableDefault(sort = {"transactionDate", "id"}, direction = Sort.Direction.DESC) Pageable pageable) {
    logger.info("Adding transaction for user id {}", user.getId());

    Profile currentProfile = profileService.findProfileByUser(user);
    if (currentProfile == null) {
      logger.error("No profile found for user id {}", user.getId());
      return "error";
    }

    if (bindingResult.hasErrors()) {
      Map<String, String> errorsMap = ControllerUtils.getErrors(bindingResult);
      model.addAttribute("errorsMap", errorsMap);
      model.addAttribute("transactionDto", transactionDto);
      logger.warn("Validation errors for transaction: {}", errorsMap);
    } else {
      Transaction transaction = transactionDtoConverter.fromDto(transactionDto, currentProfile);
      transactionService.add(transaction, currentProfile);
      currentProfile.setBalance(profileService.getCalcBalance(currentProfile));
      logger.debug("Transaction added and balance updated for user id {}: {}", user.getId(),
          currentProfile.getBalance());
    }

    setModel(model, currentProfile, transactionDtoConverter.toTransactionFilterDTO(transactionDto), pageable);

    profileService.calcBalance(user);

    return "dashboard";
  }

  private void setModel(Model model, Profile currentProfile, TransactionFilterDTO transactionFilterDTO,
      Pageable pageable) {
    logger.info("Setting model attributes for user id {}", currentProfile.getUser().getId());

    model.addAttribute("currentProfile", currentProfile);
    model.addAttribute("filters", transactionFilterDTO);

    final Page<Transaction> filteredTransactions = transactionService.filterTransactions(currentProfile.getId(),
        transactionFilterDTO, pageable);
    model.addAttribute("recentTransactions", filteredTransactions);

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

    logger.debug("Model attributes set for user id {}: incomeCategories={}, expenseCategories={}, monthIncome={}, " +
        "monthExpense={}, maxIncomeCategory={}, maxExpenseCategory={}", currentProfile
        .getUser()
        .getId(), incomeCategories.size(), expenseCategories.size(), monthIncome, monthExpense, maxIncomeCategory,
        maxExpenseCategory);
  }

}
