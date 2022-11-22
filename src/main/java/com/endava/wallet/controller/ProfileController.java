package com.endava.wallet.controller;

import com.endava.wallet.entity.Profile;
import com.endava.wallet.entity.User;
import com.endava.wallet.entity.dto.ProfileDto;
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

@Controller
@RequiredArgsConstructor
public class ProfileController {
    private final ProfileService profileService;

    private static final Logger logger = LoggerFactory.getLogger(ProfileController.class);

    @GetMapping("profile")
    public String getProfile(@AuthenticationPrincipal User user, Model model) {

        logger.info("Call for profile info page");

        Profile currentProfile = profileService.findProfileByUser(user);

        model.addAttribute("username", user.getUsername());
        model.addAttribute("firstName", currentProfile.getFirstName());
        model.addAttribute("lastName", currentProfile.getLastName());
        model.addAttribute("email", currentProfile.getEmail());

        return "profile";
    }

    @PostMapping("profile")
    public String updateProfile(
            @AuthenticationPrincipal User user,
            @RequestParam String password,
            ProfileDto profile
    ) {


        profileService.updateProfile(user, profile, password);

        return "profile";
    }
}
