package com.endava.wallet.configuration;


import com.endava.wallet.domain.User;
import com.endava.wallet.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;


    @AllArgsConstructor
    @Component
    public class UserAuthProvider implements AuthenticationProvider {

        private PasswordEncoder passwordEncoder;
        private UserRepository userRepository;

    @Override
        public Authentication authenticate(Authentication authentication) throws AuthenticationException {

            String username = authentication.getName();
            String password = authentication.getCredentials().toString();

            Optional<User> userOpt = userRepository.findUserByUsername(username);
            User user;
            if (userOpt.isPresent()){
                user = userOpt.get();
            }
            else {
                throw new BadCredentialsException("Details not found");
            }

            if (passwordEncoder.matches(password, user.getPassword())) {
                return new UsernamePasswordAuthenticationToken(username, password);
            }
            else{
            throw new BadCredentialsException("Password mismatch");
            }
        }
            @Override public boolean supports(Class<?> authentication) {
                return true;
            }
}