package com.endava.wallet.controller;

import com.endava.wallet.entity.*;
import com.endava.wallet.repository.ProfileRepository;
import com.endava.wallet.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Collections;

@Controller
@RequiredArgsConstructor
public class RegisterController {
    private final UserDtoConverter userDtoConverter;
    private final UserRepository userRepository;
    private final ProfileRepository profileRepository;
    private final ProfileDtoConverter profileDtoConverter;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @PostMapping("/register")
    public String addUser(UserDto userDto, ProfileDto profileDto, Model model) {
        if (userRepository.existsUserByUsername(userDto.getUsername()) ||
                profileRepository.existsProfileByEmail(profileDto.getEmail())) {
            model.addAttribute("error", "User already exists!");
            return "register";
        }
        User user  = userDtoConverter.fromDto(userDto);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);

        Profile profile = profileDtoConverter.fromDto(profileDto, user);
        profileRepository.save(profile);
        return "redirect:/login";
    }
}
