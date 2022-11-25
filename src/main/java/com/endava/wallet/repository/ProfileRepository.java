package com.endava.wallet.repository;

import com.endava.wallet.entity.Profile;
import com.endava.wallet.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProfileRepository extends JpaRepository<Profile, Long> {
    boolean existsProfileByEmail(String email);

    Optional<Profile> findByUser(User user);

    Profile findByActivationCode(String code);
}