package com.endava.wallet.controller;

import com.endava.wallet.entity.Authority;
import com.endava.wallet.entity.Profile;
import com.endava.wallet.entity.User;
import com.endava.wallet.repository.ProfileRepository;
import com.endava.wallet.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.Instant;
import java.util.Collections;

@Controller
@RequiredArgsConstructor
public class RegisterController {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final ProfileRepository profileRepository;

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @PostMapping("/register")
    public String addUser(User user, Profile profile, Model model) {
        if (userRepository.existsUserByUsername(user.getUsername()) ||
                profileRepository.existsProfileByEmail(profile.getEmail())) {
            model.addAttribute("error", "User already exists!");
            return "register";
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setAuthority(Collections.singleton(Authority.USER));
        user.setEnabled(true);
        userRepository.save(user);

        profile.setUser(user);
        profile.setCreatedDate(Instant.now());
        profileRepository.save(profile);
        return "redirect:/login";
    }
}
