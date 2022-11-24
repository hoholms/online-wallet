package com.endava.wallet.entity.dto;

import com.endava.wallet.entity.Authority;
import com.endava.wallet.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
@RequiredArgsConstructor
public class UserDtoConverter {

    public User fromDto(UserDto user) {
        return User.builder()
                .id(user.getId())
                .username(user.getUsername())
                .password(user.getPassword())
                .authority(Collections.singleton(Authority.USER))
                .build();
    }
}
