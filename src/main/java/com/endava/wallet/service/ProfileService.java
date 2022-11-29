package com.endava.wallet.service;

import com.endava.wallet.entity.Profile;
import com.endava.wallet.entity.User;
import com.endava.wallet.entity.dto.ProfileDto;
import com.endava.wallet.exception.ProfileNotFoundException;
import com.endava.wallet.exception.RegisterException;
import com.endava.wallet.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private static final Logger logger = LoggerFactory.getLogger(ProfileService.class);
    private final ProfileRepository profileRepository;
    private final UserService userService;
    private final MailSender mailSender;
    private final PasswordEncoder passwordEncoder;

    @Value("${hostname}")
    private String hostname;

    public void save(Profile profile) {
        profileRepository.save(profile);
    }

    public boolean add(Profile profile) {
        if (profileRepository.existsProfileByEmail(profile.getEmail())) {
            logger.error("Profile with email: {} already exists", profile.getEmail());
            return false;
        }

        profile.setActivationCode(UUID.randomUUID().toString());

        sendMail(profile);

        profileRepository.save(profile);

        return true;
    }

    public boolean activateProfile(String code) {
        Profile profile = profileRepository.findByActivationCode(code);

        if (profile == null) {
            return false;
        }

        User user = profile.getUser();

        profile.setActivationCode(null);
        user.setEnabled(true);

        userService.save(user);
        profileRepository.save(profile);

        return true;
    }

    public void updateProfile(User user, ProfileDto profileDto, String password) {
        Profile currentProfile = profileRepository.findByUser(user)
                .orElseThrow(() -> new ProfileNotFoundException(
                        String.format("Profile for user %s not found!", user.getId())
                ));

        String userEmail = currentProfile.getEmail();
        boolean isEmailChanged = (profileDto.getEmail() != null && !profileDto.getEmail().equals(userEmail) ||
                userEmail != null && !userEmail.equals(profileDto.getEmail()));

        String userFirstName = currentProfile.getFirstName();
        boolean isFirstNameChanged = (profileDto.getFirstName() != null && !profileDto.getFirstName().equals(userFirstName) ||
                userFirstName != null && !userFirstName.equals(profileDto.getFirstName()));

        String userLastName = currentProfile.getLastName();
        boolean isLastNameChanged = (profileDto.getLastName() != null && !profileDto.getLastName().equals(userLastName) ||
                userLastName != null && !userLastName.equals(profileDto.getLastName()));

        if (isFirstNameChanged) {
            currentProfile.setFirstName(profileDto.getFirstName());
        }

        if (isLastNameChanged) {
            currentProfile.setLastName(profileDto.getLastName());
        }

        if (!ObjectUtils.isEmpty(password)) {
            user.setPassword(passwordEncoder.encode(password));
        }

        if (isEmailChanged && !existsProfileByEmail(profileDto.getEmail())) {
            currentProfile.setEmail(profileDto.getEmail());
            currentProfile.setActivationCode(UUID.randomUUID().toString());
            sendMail(currentProfile);
        } else if (isEmailChanged && existsProfileByEmail(profileDto.getEmail())) {
            throw new RegisterException("Email already registered!");
        }

        userService.save(user);
        profileRepository.save(currentProfile);
    }

    public Profile findProfileByUser(User user) {
        return profileRepository.findByUser(user)
                .orElseThrow(() -> new ProfileNotFoundException(
                        String.format("Profile for user %s not found!", user.getId())
                ));
    }

    public boolean existsProfileByEmail(String email) {
        return profileRepository.existsProfileByEmail(email);
    }

    private void sendMail(Profile profile) {
        if (!ObjectUtils.isEmpty(profile.getEmail())) {
            String message = String.format(
                    """
                            Hello %s!
                            Welcome to Online Wallet!
                            Please visit this link: http://%s/activate/%s""",
                    profile.getUser().getUsername(),
                    hostname,
                    profile.getActivationCode()
            );

            mailSender.send(profile.getEmail(), "Online Wallet activation code", message);
        }
    }
}
