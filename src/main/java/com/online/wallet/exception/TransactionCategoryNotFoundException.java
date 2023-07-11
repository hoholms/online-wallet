package com.online.wallet.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class TransactionCategoryNotFoundException extends RuntimeException {
    public TransactionCategoryNotFoundException(String message) {
        super(message);
    }
}
