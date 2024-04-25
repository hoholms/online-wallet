package com.online.wallet.exception;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class ErrorResponse {

  private LocalDateTime timestamp = LocalDateTime.now();
  private int           status;
  private String        message;

  public ErrorResponse(int status, String message) {
    this.status = status;
    this.message = message;
  }

}
