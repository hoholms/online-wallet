package com.online.wallet.model.dto;

import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

import lombok.Data;

@Data
public class UsernameDto {

  @NotBlank(message = "Please provide a username")
  @Length(max = 50, message = "Username is too long")
  private final String username;

}
