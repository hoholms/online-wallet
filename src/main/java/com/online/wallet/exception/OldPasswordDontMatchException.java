package com.online.wallet.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class OldPasswordDontMatchException extends RegisterException {

  public OldPasswordDontMatchException(String message) {
    super(message);
  }

}
