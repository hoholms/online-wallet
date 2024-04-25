package com.online.wallet.controller;

import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.online.wallet.model.User;
import com.online.wallet.model.dto.CircleStatistics;
import com.online.wallet.model.dto.DateWithLabel;
import com.online.wallet.model.dto.LineStatistics;
import com.online.wallet.service.StatisticsService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class StatisticsRestController {

  private final StatisticsService statisticsService;

  @GetMapping("statistics/line")
  public List<LineStatistics> getLineStatistics(@AuthenticationPrincipal User user) {
    return statisticsService.findLineStatistics(user);
  }

  @GetMapping("statistics/circle")
  public List<CircleStatistics> getCircleStatistics(@AuthenticationPrincipal User user) {
    return statisticsService.findCircleStatistics(user);
  }

  @PostMapping("statistics/line")
  public List<LineStatistics> getLineStatisticsByDates(@AuthenticationPrincipal User user, @RequestParam String from,
      @RequestParam String to) {
    return statisticsService.findLineStatistics(user, new DateWithLabel(from), new DateWithLabel(to));
  }

  @PostMapping("statistics/circle")
  public List<CircleStatistics> getCircleStatisticsByDates(@AuthenticationPrincipal User user,
      @RequestParam String from, @RequestParam String to) {
    return statisticsService.findCircleStatistics(user, new DateWithLabel(from), new DateWithLabel(to));
  }

}
