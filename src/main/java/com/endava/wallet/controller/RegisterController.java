package com.endava.wallet.controller;

import com.endava.wallet.entity.Profile;
import com.endava.wallet.entity.User;
import com.endava.wallet.entity.dto.ProfileDto;
import com.endava.wallet.entity.dto.ProfileDtoConverter;
import com.endava.wallet.entity.dto.UserDto;
import com.endava.wallet.entity.dto.UserDtoConverter;
import com.endava.wallet.service.ProfileService;
import com.endava.wallet.service.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class RegisterController {
    private static final Logger logger = LoggerFactory.getLogger(ProfileController.class);
    private final UserDtoConverter userDtoConverter;
    private final UserService userService;
    private final ProfileService profileService;
    private final ProfileDtoConverter profileDtoConverter;

    @GetMapping("/register")
    public String register() {
        logger.info("Call for register page");
        return "register";
    }

    @PostMapping("/register")
    public String addUser(UserDto userDto, ProfileDto profileDto, Model model) {
        User user = userDtoConverter.fromDto(userDto);
        Profile profile = profileDtoConverter.fromDto(profileDto, user);

        if (!userService.add(user) || !profileService.add(profile)) {
            model.addAttribute("error", "User already exists!");
            logger.error("User not added, because he already exists");
            return "register";
        }

        return "redirect:/login";
    }

    @GetMapping("/activate/{code}")
    public String activate(Model model, @PathVariable String code) {
        boolean isActivated = profileService.activateProfile(code);

        if (isActivated) {
            model.addAttribute("success", "Your account is now activated!");
            logger.info("Account is now activated");

        } else {
            model.addAttribute("error", "Activation code is not found!");
            logger.warn("Account was not activated");
        }

        return "login";
    }
}
