package com.online.wallet.controller;

import com.online.wallet.model.Profile;
import com.online.wallet.model.Transaction;
import com.online.wallet.model.User;
import com.online.wallet.model.dto.TransactionDto;
import com.online.wallet.service.ProfileService;
import com.online.wallet.service.TransactionService;
import com.online.wallet.service.TransactionsCategoryService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@Controller
@RequestMapping("/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private static final String TRANSACTION_EDIT_ATTR = "transactionEdit";

    private static final Logger logger = LoggerFactory.getLogger(TransactionController.class);
    private final TransactionService transactionService;
    private final TransactionsCategoryService categoryService;
    private final ProfileService profileService;

    @GetMapping("{transactionID}")
    public String transactionEditForm(
            @AuthenticationPrincipal User user,
            @PathVariable Long transactionID,
            Model model
    ) {
        logger.info("Call for transaction with id: {} edit page", transactionID);

        Profile currentProfile = profileService.findProfileByUser(user);

        Transaction transaction = transactionService.findTransactionByIdAndProfile(transactionID, currentProfile);

        model.addAttribute(TRANSACTION_EDIT_ATTR, transaction);
        model.addAttribute("id", transaction.getId());
        model.addAttribute("categories",
                categoryService.findAllCategoriesByTransactionIdByIsIncome(transactionID));

        return TRANSACTION_EDIT_ATTR;
    }

    @GetMapping("/delete/{transactionID}")
    public String transactionDelete(@AuthenticationPrincipal User user, @PathVariable Long transactionID) {
        transactionService.deleteTransactionById(transactionID, user);
        logger.info("Deleted transaction with id: {}", transactionID);

        return "redirect:/dashboard";
    }

    @PostMapping()
    public String transactionSave(
            @AuthenticationPrincipal User user,
            @RequestParam Long id,
            @Valid TransactionDto transactionDto,
            BindingResult bindingResult,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errorsMap = ControllerUtils.getErrors(bindingResult);
            model.mergeAttributes(errorsMap);
            model.addAttribute(TRANSACTION_EDIT_ATTR, transactionDto);
            model.addAttribute("id", id);
            model.addAttribute("categories",
                    categoryService.findAllCategoriesByTransactionIdByIsIncome(id));
            return TRANSACTION_EDIT_ATTR;
        } else {
            transactionService.save(user, id, transactionDto);
            logger.info("Saved transaction by id {}", id);
        }

        return "redirect:/dashboard";
    }
}
