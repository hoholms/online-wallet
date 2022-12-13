package com.endava.wallet.entity.dto;

import com.endava.wallet.entity.Profile;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;

/**
 * A dto for the {@link Profile} entity
 */
@Data
public class ProfileDto implements Serializable {
    private final String firstName;
    private final String lastName;
    private final String email;
    private final BigDecimal balance;
    private final Instant createdDate;
    private final String currency;
}