package com.endava.wallet.service;

import com.endava.wallet.entity.Authority;
import com.endava.wallet.entity.User;
import com.endava.wallet.exception.ApiRequestException;
import com.endava.wallet.repository.UserRepository;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @MockBean
    private ApiRequestException apiRequestException;

    @Test
    public void addTest() {
        User user = new User();
        boolean isUserCreated = userService.add(user);
        Assert.assertTrue(isUserCreated);
        Assert.assertTrue(CoreMatchers.is(user.getAuthority()).matches(Collections.singleton(Authority.USER)));
        Mockito.verify(userRepository, Mockito.times(1)).save(user);
    }
    @Test
    public void addFailTest() {
        User user = new User();
        user.setUsername("Ivan");

        Mockito.when(userRepository.existsUserByUsername(user.getUsername()))
                .thenReturn(true);

        boolean isUserCreated = userService.add(user);


        Assert.assertFalse(isUserCreated);

        Mockito.verify(userRepository, Mockito.times(0)).save(ArgumentMatchers.any(User.class));
    }

//    @Test(expected = ApiRequestException.class)
//    public void findUserById(){
//        Mockito.when(userRepository.findUserById(0L)==null)
//                 .thenThrow(apiRequestException);
//    }

}