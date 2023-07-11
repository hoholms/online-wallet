package com.online.wallet.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalExceptionHandler {
    public static final String ERROR_404 = "error/error-404";
    public static final String DEFAULT_ERROR = "error/error";
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(TransactionNotFoundException.class)
    public String handlerTransactionNotFoundException(TransactionNotFoundException e) {
        logger.error(e.getMessage());
        return ERROR_404;
    }

    @ExceptionHandler(UserNotFoundException.class)
    public String handlerUserNotFoundException(UserNotFoundException e) {
        logger.error(e.getMessage());
        return ERROR_404;
    }

    @ExceptionHandler(TransactionCategoryNotFoundException.class)
    public String handlerTransactionCategoryNotFoundException(TransactionCategoryNotFoundException e) {
        logger.error(e.getMessage());
        return ERROR_404;
    }

    @ExceptionHandler(ProfileNotFoundException.class)
    public String handlerProfileNotFoundException(ProfileNotFoundException e) {
        logger.error(e.getMessage());
        return ERROR_404;
    }

    @ExceptionHandler(Exception.class)
    public String globalExceptionHandling(Exception e, HttpServletRequest request) {
        logger.error(e.getMessage());
        return DEFAULT_ERROR;
    }
}
