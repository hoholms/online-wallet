package com.endava.wallet.controller;

import com.endava.wallet.entity.CircleStatistics;
import com.endava.wallet.entity.LineStatistics;
import com.endava.wallet.entity.Profile;
import com.endava.wallet.entity.User;
import com.endava.wallet.service.ProfileService;
import com.endava.wallet.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
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
    public List<LineStatistics> getLineStatistics(@AuthenticationPrincipal User user) {
        Profile currentProfile = profileService.findProfileByUser(user);

        List<LocalDate> dates = transactionService.findTransactionsDates(currentProfile);

        LineStatistics incomeStatistics = new LineStatistics(new ArrayList<>(), new ArrayList<>());
        LineStatistics expenseStatistics = new LineStatistics(new ArrayList<>(), new ArrayList<>());

        for (LocalDate date : dates) {
            BigDecimal incSum = transactionService.findTranSumDateBetween(
                    currentProfile,
                    true,
                    date,
                    date.withDayOfMonth(date.getMonth().length(LocalDate.now().isLeapYear()))
            );
            incomeStatistics.getValues().add(incSum);
            incomeStatistics.getLabels().add(date.getMonth() + " " + date.getYear());

            BigDecimal expSum = transactionService.findTranSumDateBetween(
                    currentProfile,
                    false,
                    date,
                    date.withDayOfMonth(date.getMonth().length(LocalDate.now().isLeapYear()))
            );
            expenseStatistics.getValues().add(expSum);
            expenseStatistics.getLabels().add(date.getMonth() + " " + date.getYear());
        }

        List<LineStatistics> statistics = new ArrayList<>();
        Collections.reverse(incomeStatistics.getLabels());
        Collections.reverse(incomeStatistics.getValues());
        statistics.add(incomeStatistics);
        Collections.reverse(expenseStatistics.getLabels());
        Collections.reverse(expenseStatistics.getValues());
        statistics.add(expenseStatistics);

        return statistics;
    }

    @GetMapping("statistics/circle")
    public List<CircleStatistics> getCircleStatistics(@AuthenticationPrincipal User user) {
        Profile currentProfile = profileService.findProfileByUser(user);

        CircleStatistics incomeStatistics = transactionService.findCategoryAndSumByProfileAndIsIncome(currentProfile, true);
        CircleStatistics expenseStatistics = transactionService.findCategoryAndSumByProfileAndIsIncome(currentProfile, false);

        List<CircleStatistics> statistics = new ArrayList<>();
        statistics.add(incomeStatistics);
        statistics.add(expenseStatistics);

        return statistics;
    }
}
