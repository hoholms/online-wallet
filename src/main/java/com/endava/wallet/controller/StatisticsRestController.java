package com.endava.wallet.controller;

import com.endava.wallet.entity.CircleStatistics;
import com.endava.wallet.entity.DateWithLabel;
import com.endava.wallet.entity.LineStatistics;
import com.endava.wallet.entity.User;
import com.endava.wallet.service.StatisticsService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class StatisticsRestController {
    private final StatisticsService statisticsService;
    private static final Logger logger = LoggerFactory.getLogger(StatisticsRestController.class);

    @GetMapping("statistics/line")
    public List<LineStatistics> getLineStatistics(@AuthenticationPrincipal User user) {
        return statisticsService.findLineStatistics(user);
    }

    @GetMapping("statistics/circle")
    public List<CircleStatistics> getCircleStatistics(@AuthenticationPrincipal User user) {
        return statisticsService.findCircleStatistics(user);
    }

    @PostMapping("statistics/line")
    public List<LineStatistics> getLineStatisticsByDates(
            @AuthenticationPrincipal User user,
            @RequestParam String from,
            @RequestParam String to
    ) {
        return statisticsService.findLineStatistics(user, new DateWithLabel(from), new DateWithLabel(to));
    }

    @PostMapping("statistics/circle")
    public List<CircleStatistics> getCircleStatisticsByDates(
            @AuthenticationPrincipal User user,
            @RequestParam String from,
            @RequestParam String to
    ) {
        return statisticsService.findCircleStatistics(user, new DateWithLabel(from), new DateWithLabel(to));
    }
}
