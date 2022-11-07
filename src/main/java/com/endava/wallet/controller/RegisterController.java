package com.endava.wallet.controller;

import com.endava.wallet.domain.Authority;
import com.endava.wallet.domain.User;
import com.endava.wallet.service.UserServiceImpl;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collections;
import java.util.Map;

@Controller
public class RegisterController {
    private UserServiceImpl userDetailsManager;
    private PasswordEncoder passwordEncoder;

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @PostMapping(
            value = "/register",
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, produces = {
            MediaType.APPLICATION_ATOM_XML_VALUE, MediaType.APPLICATION_JSON_VALUE }
    )
    public void addUser(@RequestParam Map<String, String> body) {
        User user = new User();
        user.setUsername(body.get("username"));
        user.setPassword(passwordEncoder.encode(body.get("password")));
        user.setAuthority(Collections.singleton(Authority.USER));
        user.setAccountNonLocked(true);
        userDetailsManager.createUser(user);
    }
}
