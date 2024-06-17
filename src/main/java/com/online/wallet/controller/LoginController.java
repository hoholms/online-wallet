package com.online.wallet.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class LoginController {

  private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

  @GetMapping("/login")
  public String login(HttpServletRequest request, Model model) {
    logger.info("Login page requested");
    String errorMessage = getErrorMessage(request);
    if (errorMessage != null) {
      logger.warn("Login error: {}", errorMessage);
    }
    model.addAttribute("error", errorMessage);
    return "login";
  }

  private String getErrorMessage(HttpServletRequest request) {
    Exception exception = (Exception) request.getSession().getAttribute("SPRING_SECURITY_LAST_EXCEPTION");
    String error = null;
    if (exception != null) {
      if (exception instanceof BadCredentialsException) {
        logger.error("Login failed: Invalid username or password");
        error = "Invalid username or password!";
      } else if (exception instanceof LockedException) {
        logger.error("Login failed: Account locked - {}", exception.getMessage());
        error = exception.getMessage();
      } else {
        logger.error("Login failed: Unknown error - {}", exception.getMessage());
      }
    }
    return error;
  }

  @PostMapping("/login")
  public String loginPost() {
    logger.info("Login post request received, redirecting to dashboard");
    return "redirect:/dashboard";
  }

}
