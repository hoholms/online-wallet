package com.endava.wallet.service;

import com.endava.wallet.entity.Authority;
import com.endava.wallet.entity.User;
import com.endava.wallet.exception.UserNotFoundException;
import com.endava.wallet.repository.UserRepository;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    User user;

    @Before
    public void setUp(){
        user = new User(1L,
                "username",
                "password",
                true,
                Collections.singleton(Authority.USER));
    }
    @Test
    public void addTest() {
        boolean isUserCreated = userService.add(user);
        Assert.assertTrue(isUserCreated);
        Assert.assertTrue(CoreMatchers.is(user.getAuthority()).matches(Collections.singleton(Authority.USER)));
        Mockito.verify(userRepository, Mockito.times(1)).save(user);
    }

    @Test
    public void addFailTest() {
        user.setUsername("Ivan");

        Mockito.when(userRepository.existsUserByUsername(user.getUsername()))
                .thenReturn(true);

        boolean isUserCreated = userService.add(user);

        Assert.assertFalse(isUserCreated);

        Mockito.verify(userRepository, Mockito.times(0)).save(ArgumentMatchers.any(User.class));
    }

    @Test
    public void deleteUserByIdTest(){
        Mockito.when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        userService.deleteUserById(user.getId());
        Mockito.verify(userRepository, Mockito.times(1)).deleteById(user.getId());
    }
    @Test
    public void deleteUserByIdFailTest(){
        Mockito.when(userRepository.findById(user.getId())).thenReturn(Optional.empty());
       Assertions.assertThrows(UserNotFoundException.class, () -> userService.deleteUserById(user.getId()));
        Mockito.verify(userRepository, Mockito.times(0)).deleteById(user.getId());
    }
}