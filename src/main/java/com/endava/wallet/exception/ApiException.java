package com.endava.wallet.exception;

import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

// API Exception
public class ApiException extends Exception {

    private final String message;
    private final HttpStatus httpStatus;
    private final ZonedDateTime timeStamp;

    public ApiException(String message,
                        HttpStatus httpStatus,
                        ZonedDateTime timeStamp) {
        this.message = message;
        this.httpStatus = httpStatus;
        this.timeStamp = timeStamp;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public ZonedDateTime getTimeStamp() {
        return timeStamp;
    }
}
