package com.endava.wallet.controller;

import com.endava.wallet.domain.Authority;
import com.endava.wallet.domain.User;
import com.endava.wallet.repositories.UserRepository;
import com.endava.wallet.service.UserServiceImpl;
import lombok.Builder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Collections;
import java.util.Map;

@Controller
@Builder
public class RegisterController {
    private UserServiceImpl userDetailsManager;
    private PasswordEncoder passwordEncoder;
    private UserRepository userRepository;

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    /*@PostMapping("/register")
    public void addUser(@RequestParam Map<String, String> body) {
        User user = new User();
        user.setUsername(body.get("username"));
        user.setPassword(passwordEncoder.encode(body.get("password")));
        user.setAuthority(Collections.singleton(Authority.USER));
        user.setAccountNonLocked(true);
        userDetailsManager.createUser(user);
    }*/

    @PostMapping("/register")
    public String addUser(User user, Model model) {
        if (userRepository.existsUserByUsername(user.getUsername())) {
            model.addAttribute("message", "User already exists!");
            return "register";
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setAuthority(Collections.singleton(Authority.USER));
        user.setAccountNonLocked(true);
        userDetailsManager.createUser(user);

        return "redirect:/login";
    }
}
