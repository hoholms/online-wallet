package com.endava.wallet.controller;

import com.endava.wallet.entity.LineStatistics;
import com.endava.wallet.entity.Profile;
import com.endava.wallet.entity.User;
import com.endava.wallet.service.ProfileService;
import com.endava.wallet.service.TransactionService;
import lombok.RequiredArgsConstructor;
import net.bytebuddy.asm.Advice;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class StatisticsRestController {
    private final TransactionService transactionService;
    private final ProfileService profileService;

    @GetMapping("statistics/line")
    public List<LineStatistics> getStatistics(@AuthenticationPrincipal User user) {
        Profile currentProfile = profileService.findProfileByUser(user);

        List<LocalDate> dates = transactionService.findTransactionsDates(currentProfile);

        LineStatistics incomeStatistics = new LineStatistics(new ArrayList<String>(), new ArrayList<BigDecimal>());
        LineStatistics expenseStatistics = new LineStatistics(new ArrayList<String>(), new ArrayList<BigDecimal>());

        for (LocalDate date : dates) {
            BigDecimal IncSum = transactionService.findTranSumDateBetween(
                    currentProfile,
                    true,
                    date,
                    date.withDayOfMonth(date.getMonth().length(LocalDate.now().isLeapYear()))
            );
            incomeStatistics.getValues().add(IncSum);
            incomeStatistics.getLabels().add(date.getMonth() + " " + date.getYear());

            BigDecimal ExpSum = transactionService.findTranSumDateBetween(
                    currentProfile,
                    false,
                    date,
                    date.withDayOfMonth(date.getMonth().length(LocalDate.now().isLeapYear()))
            );
            expenseStatistics.getValues().add(ExpSum);
            expenseStatistics.getLabels().add(date.getMonth() + " " + date.getYear());
        }

        List<LineStatistics> statistics= new ArrayList<>();
        Collections.reverse(incomeStatistics.getLabels());
        Collections.reverse(incomeStatistics.getValues());
        statistics.add(incomeStatistics);
        Collections.reverse(expenseStatistics.getLabels());
        Collections.reverse(expenseStatistics.getValues());
        statistics.add(expenseStatistics);

        return statistics;
    }
}
