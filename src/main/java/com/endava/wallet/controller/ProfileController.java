package com.endava.wallet.controller;

import com.endava.wallet.entity.Currency;
import com.endava.wallet.entity.Profile;
import com.endava.wallet.entity.User;
import com.endava.wallet.entity.dto.ProfileDto;
import com.endava.wallet.exception.RegisterException;
import com.endava.wallet.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

@Controller
@RequiredArgsConstructor
public class ProfileController {
    private static final Logger logger = LoggerFactory.getLogger(ProfileController.class);
    private final ProfileService profileService;

    @GetMapping("profile")
    public String getProfile(@AuthenticationPrincipal User user, Model model) {
        logger.info("Call for profile info page by user id {}", user.getId());

        Profile currentProfile = profileService.findProfileByUser(user);

        model.addAttribute("username", user.getUsername());
        model.addAttribute("firstName", currentProfile.getFirstName());
        model.addAttribute("lastName", currentProfile.getLastName());
        model.addAttribute("email", currentProfile.getEmail());
        model.addAttribute("currency", currentProfile.getCurrency());
        model.addAttribute("currencies", Currency.values());

        return "profile";
    }

    @PostMapping("profile")
    public String updateProfile(
            HttpServletRequest request,
            @AuthenticationPrincipal User user,
            @RequestParam String password,
            ProfileDto profileDto,
            Model model
    ) {
        String response = "profile";

        try {
            response = profileService.updateProfile(request, user, profileDto, password);
            logger.info("Profile with email: {} has been updated", profileDto.getEmail());
            model.addAttribute("message", "Profile successfully updated!");
        } catch (RegisterException e) {
            model.addAttribute("error", e.getMessage());
        }

        model.addAttribute("username", user.getUsername());
        model.addAttribute("firstName", profileDto.getFirstName());
        model.addAttribute("lastName", profileDto.getLastName());
        model.addAttribute("email", profileDto.getEmail());
        model.addAttribute("currency", profileDto.getCurrency());
        model.addAttribute("currencies", Currency.values());

        return response;
    }
}
