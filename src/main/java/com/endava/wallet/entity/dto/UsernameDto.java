package com.endava.wallet.entity.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Data
public class UsernameDto {
    @NotBlank(message = "Please provide a username")
    @Length(max = 50, message = "Username is too long")
    private final String username;
}
