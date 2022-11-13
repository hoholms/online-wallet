package com.endava.wallet.service;

import com.endava.wallet.entity.Profile;
import com.endava.wallet.entity.User;
import com.endava.wallet.repository.ProfileRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ProfileService {
    private ProfileRepository profileRepository;

    public boolean existsProfileByEmail(String email) {
        return profileRepository.existsProfileByEmail(email);
    }

    public Profile findProfileByUser(User user) {
        return profileRepository.findByUser(user);
    }
}
