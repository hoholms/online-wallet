package com.online.wallet.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class UsernameAlreadyExistsException extends RegisterException {

  public UsernameAlreadyExistsException(String message) {
    super(message);
  }

}
