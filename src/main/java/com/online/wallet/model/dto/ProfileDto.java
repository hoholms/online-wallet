package com.online.wallet.model.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;

import org.hibernate.validator.constraints.Length;

import com.online.wallet.model.Profile;
import lombok.Builder;
import lombok.Data;

/**
 * A dto for the {@link Profile} entity
 */
@Data
@Builder
public class ProfileDto implements Serializable {

  @NotBlank(message = "Please provide your first name")
  @Length(max = 50, message = "First name is too long")
  private final String firstName;

  @NotBlank(message = "Please provide your last name")
  @Length(max = 50, message = "Last name is too long")
  private final String lastName;

  @NotBlank(message = "Please provide an email")
  @Email(message = "Please provide a valid email")
  private final String     email;
  private final BigDecimal balance;
  private final Instant    createdDate;
  private final String     currency;

}