package com.endava.wallet.controller;

import com.endava.wallet.entity.dto.ProfileDto;
import com.endava.wallet.entity.dto.UserDto;
import com.endava.wallet.exception.RegisterException;
import com.endava.wallet.service.ProfileService;
import com.endava.wallet.service.RegisterService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class RegisterController {
    private static final Logger logger = LoggerFactory.getLogger(RegisterController.class);
    private final ProfileService profileService;
    private final RegisterService registerService;

    @GetMapping("/register")
    public String register() {
        logger.info("Call for register page");
        return "register";
    }

    @PostMapping("/register")
    public String addUser(
            UserDto userDto,
            ProfileDto profileDto,
            @RequestParam String passwordConfirm,
            Model model
    ) {
        try {
            registerService.registerUser(userDto, profileDto, passwordConfirm);
        } catch (RegisterException e) {
            model.addAttribute("error", e.getMessage());
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
