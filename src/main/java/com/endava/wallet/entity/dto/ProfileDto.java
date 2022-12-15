package com.endava.wallet.entity.dto;

import com.endava.wallet.entity.Profile;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;

/**
 * A dto for the {@link Profile} entity
 */
@Data
public class ProfileDto implements Serializable {

    @NotBlank(message = "Please provide your first name")
    @Length(max = 50, message = "First name is too long")
    private final String firstName;

    @NotBlank(message = "Please provide your last name")
    @Length(max = 50, message = "Last name is too long")
    private final String lastName;

    @NotBlank(message = "Please provide an email")
    @Email(message = "Please provide a valid email")
    private final String email;
    private final BigDecimal balance;
    private final Instant createdDate;
    private final String currency;
}