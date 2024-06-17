package com.online.wallet.service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.online.wallet.exception.EmailAlreadyExistsException;
import com.online.wallet.exception.OldPasswordDontMatchException;
import com.online.wallet.exception.PasswordsDontMatchException;
import com.online.wallet.exception.ProfileNotFoundException;
import com.online.wallet.model.Profile;
import com.online.wallet.model.User;
import com.online.wallet.model.dto.PasswordChangeDto;
import com.online.wallet.model.dto.ProfileDto;
import com.online.wallet.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProfileService {

  private static final Logger            logger = LoggerFactory.getLogger(ProfileService.class);
  private final        ProfileRepository profileRepository;
  private final        UserService       userService;
  private final        MailSender        mailSender;
  private final        PasswordEncoder   passwordEncoder;

  @Value("${hostname}")
  private String hostname;

  public void save(Profile profile) {
    profileRepository.save(profile);
    logger.info("Profile saved: {}", profile);
  }

  public boolean add(Profile profile) {
    if (profileRepository.existsProfileByEmail(profile.getEmail())) {
      logger.error("Profile with email: {} already exists", profile.getEmail());
      return false;
    }

    profile.setCurrency("USD");
    profile.setActivationCode(UUID.randomUUID().toString());
    profileRepository.save(profile);
    sendMail(profile);
    logger.info("Profile added: {}", profile);

    return true;
  }

  private void sendMail(Profile profile) {
    if (!ObjectUtils.isEmpty(profile.getEmail())) {
      String message = String.format("""
                                     Hello %s!
                                     Welcome to Online Wallet!
                                     Please visit this link: http://%s/activate/%s""", profile
          .getUser()
          .getUsername(), hostname, profile.getActivationCode());

      mailSender.send(profile.getEmail(), "Online Wallet activation code", message);
      logger.info("Activation email sent to: {}", profile.getEmail());
    }
  }

  public boolean activateProfile(String code) {
    Profile profile = profileRepository.findByActivationCode(code);

    if (profile == null) {
      logger.warn("Activation code {} not found", code);
      return false;
    }

    User user = profile.getUser();

    profile.setActivationCode(null);
    user.setEnabled(true);

    userService.save(user);
    profileRepository.save(profile);
    logger.info("Profile activated: {}", profile);

    return true;
  }

  public String updateProfile(HttpServletRequest request, User user, ProfileDto profileDto,
      PasswordChangeDto passwordChangeDto) {
    Profile currentProfile = profileRepository
        .findByUser(user)
        .orElseThrow(() -> new ProfileNotFoundException(String.format("Profile for user %s not found!", user.getId())));

    String userEmail = currentProfile.getEmail();
    boolean isEmailChanged = (profileDto.getEmail() != null && !profileDto.getEmail().equals(userEmail) ||
                                  userEmail != null && !userEmail.equals(profileDto.getEmail()));

    String userFirstName = currentProfile.getFirstName();
    boolean isFirstNameChanged = (
        profileDto.getFirstName() != null && !profileDto.getFirstName().equals(userFirstName) ||
            userFirstName != null && !userFirstName.equals(profileDto.getFirstName()));

    String userLastName = currentProfile.getLastName();
    boolean isLastNameChanged = (profileDto.getLastName() != null && !profileDto.getLastName().equals(userLastName) ||
                                     userLastName != null && !userLastName.equals(profileDto.getLastName()));

    boolean isPasswordChanged = !ObjectUtils.isEmpty(passwordChangeDto.getNewPassword());

    if (isFirstNameChanged) {
      currentProfile.setFirstName(profileDto.getFirstName());
    }

    if (isLastNameChanged) {
      currentProfile.setLastName(profileDto.getLastName());
    }

    if (isPasswordChanged) {
      if (!BCrypt.checkpw(passwordChangeDto.getOldPassword(), user.getPassword())) {
        logger.error("Old password doesn't match for user {}", user.getId());
        throw new OldPasswordDontMatchException("Old password is incorrect");
      } else if (!passwordChangeDto.getNewPassword().equals(passwordChangeDto.getConfirmPassword())) {
        logger.error("New passwords don't match for user {}", user.getId());
        throw new PasswordsDontMatchException("Passwords don't match");
      }

      user.setPassword(passwordEncoder.encode(passwordChangeDto.getNewPassword()));
    }

    if (isEmailChanged && !existsProfileByEmail(profileDto.getEmail())) {
      currentProfile.setEmail(profileDto.getEmail());
      currentProfile.setActivationCode(UUID.randomUUID().toString());
      sendMail(currentProfile);
      user.setEnabled(false);
      logger.info("Profile id {} has been updated with new email", currentProfile.getId());
    } else if (isEmailChanged && existsProfileByEmail(profileDto.getEmail())) {
      logger.error("Email update failed for profile id {}: email already exists", currentProfile.getId());
      throw new EmailAlreadyExistsException("Email already registered!");
    }

    currentProfile.setCurrency(profileDto.getCurrency());

    userService.save(user);
    profileRepository.save(currentProfile);
    logger.info("Profile updated: {}", currentProfile);

    if (isEmailChanged || isPasswordChanged) {
      try {
        request.logout();
        logger.info("User {} logged out due to email or password change", user.getId());
        return "redirect:/login";
      } catch (ServletException e) {
        logger.error("Logout failed for user {}: {}", user.getId(), e.getMessage());
      }
    }

    return "profile";
  }

  public boolean existsProfileByEmail(String email) {
    boolean exists = profileRepository.existsProfileByEmail(email);
    logger.debug("Check if email exists: {} - {}", email, exists);
    return exists;
  }

  public void calcBalance(User user) {
    Profile profile = findProfileByUser(user);
    profileRepository.calcBalance(profile.getId());
    logger.info("Calculated balance for profile id {}", profile.getId());
  }

  public Profile findProfileByUser(User user) {
    Profile profile = profileRepository
        .findByUser(user)
        .orElseThrow(() -> new ProfileNotFoundException(String.format("Profile for user %s not found!", user.getId())));
    logger.debug("Found profile for user {}: {}", user.getId(), profile);
    return profile;
  }

  public BigDecimal getCalcBalance(Profile profile) {
    BigDecimal balance = profileRepository.getCalcBalance(profile.getId());
    logger.debug("Calculated balance for profile id {}: {}", profile.getId(), balance);
    return balance;
  }

}
