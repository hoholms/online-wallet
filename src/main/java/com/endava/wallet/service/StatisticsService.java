package com.endava.wallet.service;

import com.endava.wallet.entity.CircleStatistics;
import com.endava.wallet.entity.LineStatistics;
import com.endava.wallet.entity.Profile;
import com.endava.wallet.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StatisticsService {
    private final ProfileService profileService;
    private final TransactionService transactionService;

    public List<LineStatistics> findLineStatistics(User user) {
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
        statistics.add(incomeStatistics);
        statistics.add(expenseStatistics);

        return statistics;
    }

    public List<CircleStatistics> findCircleStatistics(User user) {
        Profile currentProfile = profileService.findProfileByUser(user);

        CircleStatistics incomeStatistics = transactionService.findCategoryAndSumByProfileAndIsIncome(currentProfile, true);
        CircleStatistics expenseStatistics = transactionService.findCategoryAndSumByProfileAndIsIncome(currentProfile, false);

        List<CircleStatistics> statistics = new ArrayList<>();
        statistics.add(incomeStatistics);
        statistics.add(expenseStatistics);

        return statistics;
    }
}
