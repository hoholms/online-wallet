package com.endava.wallet.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class StatisticsController {

    @GetMapping("statistics")
    public String statistics() {
        return "statistics";
    }
}
