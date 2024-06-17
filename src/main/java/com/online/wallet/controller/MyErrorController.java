package com.online.wallet.controller;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MyErrorController implements org.springframework.boot.web.servlet.error.ErrorController {

  private static final Logger logger = LoggerFactory.getLogger(MyErrorController.class);

  @RequestMapping("/error")
  public String handleError(HttpServletRequest request) {
    Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

    if (status != null) {
      int statusCode = Integer.parseInt(status.toString());

      if (statusCode == HttpStatus.FORBIDDEN.value()) {
        logger.warn("Error 403: Forbidden");
        return "error/error-403";
      } else if (statusCode == HttpStatus.NOT_FOUND.value()) {
        logger.warn("Error 404: Not Found");
        return "error/error-404";
      } else if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
        logger.error("Error 500: Internal Server Error");
        return "error/error-500";
      } else {
        logger.error("Unexpected error with status code: {}", statusCode);
      }
    } else {
      logger.error("No status code found in the request attributes");
    }
    return "error/error";
  }

}
