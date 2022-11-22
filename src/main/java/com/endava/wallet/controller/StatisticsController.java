package com.endava.wallet.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;

@Controller
@RequiredArgsConstructor
public class StatisticsController {
    @GetMapping("statistics")
    public String statistics(Model model) {
        model.addAttribute("today", LocalDate.now());

        return "statistics";
    }
}
