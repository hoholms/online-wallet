package com.endava.wallet.entity.DTO;

import com.endava.wallet.entity.Profile;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;

/**

 * A DTO for the {@link Profile} entity

 */
@Data
public class ProfileDto implements Serializable {
    private final String firstName;
    private final String lastName;
    private final String email;
    private final BigDecimal balance;
    private final Instant createdDate;
}