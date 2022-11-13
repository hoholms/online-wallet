package com.endava.wallet.controller;

import com.endava.wallet.entity.Profile;
import com.endava.wallet.entity.Transaction;
import com.endava.wallet.entity.User;
import com.endava.wallet.repository.ProfileRepository;
import com.endava.wallet.service.ProfileService;
import com.endava.wallet.service.TransactionsService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@Controller
@RequestMapping("/transactions")
@AllArgsConstructor
public class TransactionController {
    private TransactionsService transactionsService;
    private ProfileService profileService;
    private ProfileRepository profileRepository;

    @GetMapping
    public String transactionsList(@AuthenticationPrincipal User user, Model model) {
        Profile profile = profileService.findProfileByUser(user);
        model.addAttribute("transactions", transactionsService.findTransactionByProfileOrderByIdAsc(profile));
        model.addAttribute("userID", user.getId());
        return "transactions";
    }

    @GetMapping("{transactionID}")
    public String transactionEditForm(@PathVariable Long transactionID, Model model) {
        model.addAttribute("transactionEdit", transactionsService.findTransactionById(transactionID));
        return "transactionEdit";
    }

    @GetMapping("/delete/{transactionID}")
    public String transactionDelete(@PathVariable Long transactionID, @AuthenticationPrincipal User user, Model model) {
        Transaction transaction = transactionsService.findTransactionById(transactionID);
        transactionsService.deleteById(transactionID);
        Profile profile = profileService.findProfileByUser(user);
        if (Boolean.TRUE.equals(transaction.getIsIncome())) {
            profile.setBalance(profile.getBalance().subtract(transaction.getAmount()));
        } else {
            profile.setBalance(profile.getBalance().add(transaction.getAmount()));
        }
        profileRepository.save(profile);
        model.addAttribute("transactions", transactionsService.findTransactionByProfileOrderByIdAsc(profile));
        return "transactions";
    }

    @PostMapping
    public String transactionSave(
            @AuthenticationPrincipal User user,
            @RequestParam Long id,
            @RequestParam(required = false) String message,
            @RequestParam(required = false) BigDecimal amount,
            @RequestParam String transactionDate
    ) {
        Profile profile = profileService.findProfileByUser(user);
        Transaction transaction = transactionsService.findTransactionById(id);
        if (amount != null && !amount.equals(transaction.getAmount())) {
            if (Boolean.TRUE.equals(transaction.getIsIncome())) {
                profile.setBalance(profile.getBalance().subtract(transaction.getAmount()));
                profile.setBalance(profile.getBalance().add(amount));
            } else {
                profile.setBalance(profile.getBalance().add(transaction.getAmount()));
                profile.setBalance(profile.getBalance().subtract(amount));
            }
        }

        if (amount != null) {
            transaction.setAmount(amount);
        }
        transaction.setTransactionDate(transactionsService.parseDate(transactionDate));
        transaction.setMessage(message);

        transactionsService.save(transaction);
        profileRepository.save(profile);

        return "redirect:/transactions";
    }

}
