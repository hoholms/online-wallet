package com.endava.wallet.service;

import com.endava.wallet.entity.Authority;
import com.endava.wallet.entity.Profile;
import com.endava.wallet.entity.User;
import com.endava.wallet.entity.dto.ProfileDto;
import com.endava.wallet.entity.dto.UserDto;
import com.endava.wallet.entity.dto.UserDtoConverter;
import com.endava.wallet.exception.RegisterException;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertThrows;
@RunWith(SpringRunner.class)
@SpringBootTest
class RegisterServiceTest {
    @Autowired
    private RegisterService registerService;
    @MockBean
    private UserService userService;
    @MockBean
    private ProfileService profileService;
    @MockBean
            private UserDtoConverter userDtoConverter;


    User user = User.builder()
            .id(1L)
                .username("username")
                .password("password")
                .enabled(true)
                .authority(Collections.singleton(Authority.USER))
            .build();
    Profile profile = Profile.builder()
            .user(user)
                .email("w@w")
                .build();

    @Test
     void registerTest(){
        UserDto userDto = Mockito.mock(UserDto.class);
        ProfileDto profileDto = Mockito.mock(ProfileDto.class);
        Mockito.when(userDtoConverter.fromDto(userDto)).thenReturn(user);
        registerService.registerUser(userDto, profileDto, "password");
        Mockito.verify(userService, Mockito.times(1)).add(user);
    }

    @Test()
    void failedRegisterTest(){
        UserDto userDto = Mockito.mock(UserDto.class);
        ProfileDto profileDto = Mockito.mock(ProfileDto.class);
        Mockito.when(userDtoConverter.fromDto(userDto)).thenReturn(user);
        Mockito.when(userService.existsUserByUsername(user.getUsername())).thenReturn(true);
        Mockito.when(profileService.existsProfileByEmail(profile.getEmail())).thenReturn(true);
        assertThrows(RegisterException.class, () -> registerService.registerUser(userDto, profileDto, "password"));
        Mockito.verify(userService, Mockito.times(0)).add(user);
    }
}