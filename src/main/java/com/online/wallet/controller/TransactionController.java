package com.online.wallet.controller;

import javax.validation.Valid;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.online.wallet.model.Profile;
import com.online.wallet.model.Transaction;
import com.online.wallet.model.User;
import com.online.wallet.model.dto.TransactionDto;
import com.online.wallet.service.ProfileService;
import com.online.wallet.service.TransactionService;
import com.online.wallet.service.TransactionsCategoryService;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/transactions")
@RequiredArgsConstructor
public class TransactionController {

  private static final String TRANSACTION_EDIT_ATTR = "transactionEdit";

  private static final Logger                      logger = LoggerFactory.getLogger(TransactionController.class);
  private final        TransactionService          transactionService;
  private final        TransactionsCategoryService categoryService;
  private final        ProfileService              profileService;

  @GetMapping("{transactionID}")
  public String transactionEditForm(@AuthenticationPrincipal User user, @PathVariable Long transactionID, Model model) {
    logger.info("Call for transaction edit page with id: {}", transactionID);

    Profile currentProfile = profileService.findProfileByUser(user);

    Transaction transaction = transactionService.findTransactionByIdAndProfile(transactionID, currentProfile);
    if (transaction == null) {
      logger.warn("Transaction with id {} not found for user {}", transactionID, user.getId());
      return "redirect:/dashboard";
    }

    model.addAttribute(TRANSACTION_EDIT_ATTR, transaction);
    model.addAttribute("id", transaction.getId());
    model.addAttribute("categories", categoryService.findAllCategoriesByTransactionIdByIsIncome(transactionID));
    logger.debug("Editing transaction with id: {}, user: {}", transactionID, user.getId());

    return TRANSACTION_EDIT_ATTR;
  }

  @GetMapping("/delete/{transactionID}")
  public String transactionDelete(@AuthenticationPrincipal User user, @PathVariable Long transactionID) {
    transactionService.deleteTransactionById(transactionID, user);
    logger.info("Deleted transaction with id: {}", transactionID);

    return "redirect:/dashboard";
  }

  @PostMapping()
  public String transactionSave(@AuthenticationPrincipal User user, @RequestParam Long id,
      @Valid TransactionDto transactionDto, BindingResult bindingResult, Model model) {
    if (bindingResult.hasErrors()) {
      Map<String, String> errorsMap = ControllerUtils.getErrors(bindingResult);
      model.mergeAttributes(errorsMap);
      model.addAttribute(TRANSACTION_EDIT_ATTR, transactionDto);
      model.addAttribute("id", id);
      model.addAttribute("categories", categoryService.findAllCategoriesByTransactionIdByIsIncome(id));
      logger.warn("Validation errors while saving transaction with id {}: {}", id, errorsMap);
      return TRANSACTION_EDIT_ATTR;
    } else {
      transactionService.save(user, id, transactionDto);
      logger.info("Saved transaction with id {}", id);
    }

    return "redirect:/dashboard";
  }

}
