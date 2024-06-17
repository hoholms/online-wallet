package com.online.wallet.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

  private static final Logger            logger = LoggerFactory.getLogger(StatisticsRestController.class);
  private final        StatisticsService statisticsService;

  @GetMapping("statistics/line")
  public List<LineStatistics> getLineStatistics(@AuthenticationPrincipal User user) {
    logger.info("Fetching line statistics for user id {}", user.getId());
    List<LineStatistics> lineStatistics = statisticsService.findLineStatistics(user);
    logger.debug("Retrieved line statistics for user id {}: {}", user.getId(), lineStatistics);
    return lineStatistics;
  }

  @GetMapping("statistics/circle")
  public List<CircleStatistics> getCircleStatistics(@AuthenticationPrincipal User user) {
    logger.info("Fetching circle statistics for user id {}", user.getId());
    List<CircleStatistics> circleStatistics = statisticsService.findCircleStatistics(user);
    logger.debug("Retrieved circle statistics for user id {}: {}", user.getId(), circleStatistics);
    return circleStatistics;
  }

  @PostMapping("statistics/line")
  public List<LineStatistics> getLineStatisticsByDates(@AuthenticationPrincipal User user, @RequestParam String from,
      @RequestParam String to) {
    logger.info("Fetching line statistics for user id {} from {} to {}", user.getId(), from, to);
    List<LineStatistics> lineStatistics = statisticsService.findLineStatistics(user, new DateWithLabel(from),
        new DateWithLabel(to));
    logger.debug("Retrieved line statistics for user id {} from {} to {}: {}", user.getId(), from, to, lineStatistics);
    return lineStatistics;
  }

  @PostMapping("statistics/circle")
  public List<CircleStatistics> getCircleStatisticsByDates(@AuthenticationPrincipal User user,
      @RequestParam String from, @RequestParam String to) {
    logger.info("Fetching circle statistics for user id {} from {} to {}", user.getId(), from, to);
    List<CircleStatistics> circleStatistics = statisticsService.findCircleStatistics(user, new DateWithLabel(from),
        new DateWithLabel(to));
    logger.debug("Retrieved circle statistics for user id {} from {} to {}: {}", user.getId(), from, to,
        circleStatistics);
    return circleStatistics;
  }

}
