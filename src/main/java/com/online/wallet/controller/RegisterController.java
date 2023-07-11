package com.online.wallet.controller;

import com.online.wallet.exception.EmailAlreadyExistsException;
import com.online.wallet.exception.PasswordsDontMatchException;
import com.online.wallet.exception.RegisterException;
import com.online.wallet.exception.UsernameAlreadyExistsException;
import com.online.wallet.model.dto.ProfileDto;
import com.online.wallet.model.dto.UserDto;
import com.online.wallet.service.ProfileService;
import com.online.wallet.service.RegisterService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class RegisterController {

    private static final String REGISTER_PAGE = "register";
    private static final String MESSAGE_ATTR = "message";

    private static final Logger logger = LoggerFactory.getLogger(RegisterController.class);
    private final ProfileService profileService;
    private final RegisterService registerService;

    @GetMapping("/register")
    public String register() {
        logger.info("Call for register page");
        return REGISTER_PAGE;
    }

    @PostMapping("/register")
    public String addUser(
            @RequestParam String passwordConfirm,
            @Valid UserDto userDto,
            BindingResult userBindingResult,
            @Valid ProfileDto profileDto,
            BindingResult profileBindingResult,
            Model model
    ) {
        if (profileBindingResult.hasErrors() || userBindingResult.hasErrors()) {
            Map<String, String> profileErrorsMap = ControllerUtils.getErrors(profileBindingResult);
            Map<String, String> userErrorsMap = ControllerUtils.getErrors(userBindingResult);
            model.mergeAttributes(profileErrorsMap);
            model.mergeAttributes(userErrorsMap);
            model.addAttribute("userDto", userDto);
            model.addAttribute("profileDto", profileDto);
        } else {
            try {
                registerService.registerUser(userDto, profileDto, passwordConfirm);
            } catch (RegisterException e) {
                if (e.getClass() == UsernameAlreadyExistsException.class) {
                    model.addAttribute("usernameError", e.getMessage());
                } else if (e.getClass() == EmailAlreadyExistsException.class) {
                    model.addAttribute("emailError", e.getMessage());
                } else if (e.getClass() == PasswordsDontMatchException.class) {
                    model.addAttribute("passwordConfirmError", e.getMessage());
                }

                model.addAttribute("userDto", userDto);
                model.addAttribute("profileDto", profileDto);

                return REGISTER_PAGE;
            }

            model.addAttribute(MESSAGE_ATTR, "Now confirm your email!");
            return "login";
        }

        return REGISTER_PAGE;
    }

    @GetMapping("/activate/{code}")
    public String activate(Model model, @PathVariable String code) {
        boolean isActivated = profileService.activateProfile(code);
        model.addAttribute("isActivated", isActivated);

        if (isActivated) {
            model.addAttribute(MESSAGE_ATTR, "Your account is now activated!");
            logger.info("Account is now activated");

        } else {
            model.addAttribute(MESSAGE_ATTR, "Activation code is not found!");
            logger.warn("Account was not activated");
        }

        return "info";
    }
}
