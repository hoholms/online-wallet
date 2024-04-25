package com.online.wallet.model.dto;

import java.util.Collections;

import org.springframework.stereotype.Component;

import com.online.wallet.model.Authority;
import com.online.wallet.model.User;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UserDtoConverter {

  public User fromDto(UserDto user) {
    return User
        .builder()
        .id(user.getId())
        .username(user.getUsername())
        .password(user.getPassword())
        .authority(Collections.singleton(Authority.USER))
        .build();
  }

}
