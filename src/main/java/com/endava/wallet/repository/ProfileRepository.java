package com.endava.wallet.repository;

import com.endava.wallet.entity.Profile;
import com.endava.wallet.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileRepository extends JpaRepository<Profile, String> {
}
