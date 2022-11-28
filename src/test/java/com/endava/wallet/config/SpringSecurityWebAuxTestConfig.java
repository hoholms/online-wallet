package com.endava.wallet.config;

import com.endava.wallet.entity.Authority;
import com.endava.wallet.entity.User;
import com.endava.wallet.service.UserService;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import java.util.Arrays;
import java.util.Collections;

@TestConfiguration
public class SpringSecurityWebAuxTestConfig {
    PasswordEncoder passwordEncoder;
    UserService userService;
    @Bean
    @Primary
    public UserDetailsService userDetailsService() {
        User admin = new User(1L,
                "admin",
                passwordEncoder.encode(userService.loadUserByUsername("admin").getPassword()),
                true,
                Collections.singleton(Authority.USER));
//        User simpleUser = new User(1L, )

        return new InMemoryUserDetailsManager(Arrays.asList(admin));
    }
}
