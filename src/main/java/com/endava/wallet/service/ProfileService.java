package com.endava.wallet.service;

import com.endava.wallet.entity.Profile;
import com.endava.wallet.entity.User;
import com.endava.wallet.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProfileService {
    private final ProfileRepository profileRepository;
    private final UserService userService;
    private final MailSender mailSender;


    public Profile findProfileByUser(User user) {
        return profileRepository.findByUser(user);
    }

    public Boolean existsProfileByEmail(String email) {
        return profileRepository.existsProfileByEmail(email);
    }

    public void save(Profile profile) {
        profileRepository.save(profile);
    }

    public boolean add(Profile profile) {
        if (profileRepository.existsProfileByEmail(profile.getEmail())) {
            return false;
        }

        profile.setActivationCode(UUID.randomUUID().toString());

        if (!ObjectUtils.isEmpty(profile.getEmail())) {
            String message = String.format(
                    """
                            Hello %s!
                            Welcome to Sweater!
                            Please visit this link: http://localhost:8080/activate/%s""",
                    profile.getUser().getUsername(),
                    profile.getActivationCode()
            );

            mailSender.send(profile.getEmail(), "Sweater activation code", message);
        }

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
}
