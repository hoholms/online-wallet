package com.endava.wallet.controller;

import com.endava.wallet.domain.Authority;
import com.endava.wallet.domain.User;
import com.endava.wallet.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Collections;

@Controller
@RequiredArgsConstructor
public class RegisterController {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @PostMapping("/register")
    public String addUser(User user, Model model) {
        if (userRepository.existsUserByUsername(user.getUsername())) {
            model.addAttribute("message", "User already exists!");
            return "register";
        }
        model.addAttribute(user.getUsername(), "username");
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setAuthority(Collections.singleton(Authority.USER));
        user.setEnabled(true);
        userRepository.save(user);

        return "redirect:/login";
    }
}
