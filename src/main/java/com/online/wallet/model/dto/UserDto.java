package com.online.wallet.model.dto;

import com.online.wallet.model.User;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Null;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 * A dto for the {@link User} entity
 */
@Data
public class UserDto implements Serializable {

    @Null
    private final Long id;

    @NotBlank(message = "Please provide a username")
    @Length(max = 50, message = "Username is too long")
    private final String username;

    @NotBlank(message = "Please provide a password")
    @Length(min = 8, max = 500, message = "Password must be at least 8 characters")
    @Pattern(regexp = "^(?=.*\\d)(?=.*[a-z])(?=\\S+$).{8,}$", message = "Password must have at least 1 number and 1 letter")
    private final String password;
}