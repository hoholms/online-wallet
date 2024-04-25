package com.online.wallet.controller;

import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ControllerUtils {

  static Map<String, String> getErrors(BindingResult bindingResult) {
    Collector<FieldError, ?, Map<String, String>> collector = Collectors.toMap(fieldError -> fieldError.getField() +
        "Error", FieldError::getDefaultMessage, (fieldError1, fieldError2) -> fieldError1 + ". " + fieldError2);

    return bindingResult.getFieldErrors().stream().collect(collector);
  }

}
