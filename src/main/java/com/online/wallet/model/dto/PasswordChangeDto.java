package com.online.wallet.model.dto;

import javax.validation.constraints.Pattern;

import lombok.Data;

@Data
public class PasswordChangeDto {


  private final String oldPassword;

  @Pattern(regexp = "|^(?=.*\\d)(?=.*[a-z])(?=\\S+$).{8,}$",
      message = "Password must have at least 1 number and 1 letter and be at least 8 characters long")
  private final String newPassword;

  private final String confirmPassword;

}
