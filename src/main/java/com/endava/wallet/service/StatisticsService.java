package com.endava.wallet.service;

import com.endava.wallet.entity.*;
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

        List<DateWithLabel> dates = transactionService.findTransactionsDatesWithLabels(currentProfile);

        return getLineStatistics(currentProfile, dates);
    }

    public List<LineStatistics> findLineStatistics(User user, DateWithLabel from, DateWithLabel to) {
        Profile currentProfile = profileService.findProfileByUser(user);

        if (from.getDate().isAfter(to.getDate())) {
            DateWithLabel tmp = from;
            from = to;
            to = tmp;
        }

        List<DateWithLabel> dates = transactionService.findTransactionsDatesWithLabels(currentProfile, from, to);

        return getLineStatistics(currentProfile, dates);
    }

    private List<LineStatistics> getLineStatistics(Profile currentProfile, List<DateWithLabel> dates) {
        LineStatistics incomeStatistics = new LineStatistics(new ArrayList<>(), new ArrayList<>());
        LineStatistics expenseStatistics = new LineStatistics(new ArrayList<>(), new ArrayList<>());

        for (DateWithLabel date : dates) {
            BigDecimal incSum = transactionService.findTranSumDateBetween(
                    currentProfile,
                    true,
                    date.getDate(),
                    date.getDate().withDayOfMonth(date.getDate().getMonth().length(LocalDate.now().isLeapYear()))
            );
            incomeStatistics.getValues().add(incSum);
            incomeStatistics.getLabels().add(date.getLabel());

            BigDecimal expSum = transactionService.findTranSumDateBetween(
                    currentProfile,
                    false,
                    date.getDate(),
                    date.getDate().withDayOfMonth(date.getDate().getMonth().length(LocalDate.now().isLeapYear()))
            );
            expenseStatistics.getValues().add(expSum);
            expenseStatistics.getLabels().add(date.getLabel());
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

    public List<CircleStatistics> findCircleStatistics(User user, DateWithLabel from, DateWithLabel to) {
        Profile currentProfile = profileService.findProfileByUser(user);

        if (from.getDate().isAfter(to.getDate())) {
            DateWithLabel tmp = from;
            from = to;
            to = tmp;
        }

        to.setDate(to.getDate().withDayOfMonth(to.getDate().getMonth().length(LocalDate.now().isLeapYear())));

        CircleStatistics incomeStatistics = transactionService.findCategoryAndSumByProfileAndIsIncome(currentProfile, true, from, to);
        CircleStatistics expenseStatistics = transactionService.findCategoryAndSumByProfileAndIsIncome(currentProfile, false, from, to);

        List<CircleStatistics> statistics = new ArrayList<>();
        statistics.add(incomeStatistics);
        statistics.add(expenseStatistics);

        return statistics;
    }
}
