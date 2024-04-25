package com.online.wallet.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.online.wallet.exception.EmailAlreadyExistsException;
import com.online.wallet.exception.OldPasswordDontMatchException;
import com.online.wallet.exception.PasswordsDontMatchException;
import com.online.wallet.model.Profile;
import com.online.wallet.model.User;
import com.online.wallet.model.dto.Currency;
import com.online.wallet.model.dto.PasswordChangeDto;
import com.online.wallet.model.dto.ProfileDto;
import com.online.wallet.model.dto.ProfileDtoConverter;
import com.online.wallet.service.ProfileService;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ProfileController {

  private static final Logger              logger = LoggerFactory.getLogger(ProfileController.class);
  private final        ProfileService      profileService;
  private final        ProfileDtoConverter profileDtoConverter;

  @GetMapping("profile")
  public String getProfile(@AuthenticationPrincipal User user, Model model) {
    logger.info("Call for profile info page by user id {}", user.getId());

    Profile currentProfile = profileService.findProfileByUser(user);

    model.addAttribute("username", user.getUsername());
    model.addAttribute("profileDto", profileDtoConverter.toDto(currentProfile));
    model.addAttribute("currencies", Currency.values());

    return "profile";
  }

  @PostMapping("profile")
  public String updateProfile(HttpServletRequest request, @AuthenticationPrincipal User user,
      @Valid PasswordChangeDto passwordChangeDto, BindingResult passwordBindingResult, @Valid ProfileDto profileDto,
      BindingResult profileBindingResult, Model model) {
    String response = "profile";

    if (passwordBindingResult.hasErrors() || profileBindingResult.hasErrors()) {
      Map<String, String> passwordErrorsMap = ControllerUtils.getErrors(passwordBindingResult);
      Map<String, String> profileErrorsMap = ControllerUtils.getErrors(profileBindingResult);
      model.mergeAttributes(passwordErrorsMap);
      model.mergeAttributes(profileErrorsMap);
    } else {
      try {
        response = profileService.updateProfile(request, user, profileDto, passwordChangeDto);
        logger.info("User {} profile has been updated", user.getUsername());
        model.addAttribute("message", "Profile successfully updated!");
      } catch (OldPasswordDontMatchException e) {
        model.addAttribute("oldPasswordError", e.getMessage());
      } catch (PasswordsDontMatchException e) {
        model.addAttribute("confirmPasswordError", e.getMessage());
      } catch (EmailAlreadyExistsException e) {
        model.addAttribute("emailError", e.getMessage());
      }
    }

    model.addAttribute("username", user.getUsername());
    model.addAttribute("profileDto", profileDto);
    model.addAttribute("currencies", Currency.values());

    return response;
  }

}
