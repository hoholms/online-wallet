package com.online.wallet.model.dto;

import com.online.wallet.model.Profile;
import com.online.wallet.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.Instant;

@Component
@RequiredArgsConstructor
public class ProfileDtoConverter {

    public Profile fromDto(ProfileDto profile, User user) {
        return Profile.builder()
                .user(user)
                .firstName(profile.getFirstName())
                .lastName(profile.getLastName())
                .email(profile.getEmail())
                .balance(BigDecimal.ZERO)
                .createdDate(Instant.now())
                .currency(profile.getCurrency())
                .build();
    }

    public ProfileDto toDto(Profile profile) {
        return ProfileDto.builder()
                .firstName(profile.getFirstName())
                .lastName(profile.getLastName())
                .email(profile.getEmail())
                .balance(profile.getBalance())
                .createdDate(profile.getCreatedDate())
                .currency(profile.getCurrency())
                .build();
    }
}
