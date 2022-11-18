package com.endava.wallet.entity.dto;

import com.endava.wallet.entity.Profile;
import com.endava.wallet.entity.User;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.Instant;

@Component
public class ProfileDtoConverter {

    public Profile fromDto(ProfileDto profile, User user) {
        return Profile.builder()
                .user(user)
                .firstName(profile.getFirstName())
                .lastName(profile.getLastName())
                .email(profile.getEmail())
                .balance(BigDecimal.ZERO)
                .createdDate(Instant.now())
                .build();
    }
}
