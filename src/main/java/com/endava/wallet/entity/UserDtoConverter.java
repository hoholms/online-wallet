package com.endava.wallet.entity;

import com.endava.wallet.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
@RequiredArgsConstructor
public class UserDtoConverter {
    private final PasswordEncoder passwordEncoder;

    public User fromDto(UserDto user) {
        return User.builder()
                .id(user.getId())
                .username(user.getUsername())
                .password(user.getPassword())
                .authority(Collections.singleton(Authority.USER))
                .enabled(true)
                .build();
    }
}
