package com.online.wallet.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class WalletController {

  @GetMapping("/")
  public String hello() {
    return "index";
  }

}
