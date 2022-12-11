package com.endava.wallet.repository;

import com.endava.wallet.entity.Profile;
import com.endava.wallet.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

public interface ProfileRepository extends JpaRepository<Profile, Long> {
    boolean existsProfileByEmail(String email);

    Optional<Profile> findByUser(User user);

    Profile findByActivationCode(String code);

    @Modifying
    @Transactional
    @Query(value = """
            UPDATE profiles
            SET balance = calc_balance(:profile_id)
            WHERE id = :profile_id
            """, nativeQuery = true)
    void calcBalance(@Param("profile_id") long profileId);

    @Query(value = """
            SELECT calc_balance(:profile_id)
            """, nativeQuery = true)
    BigDecimal getCalcBalance(@Param("profile_id") long profileId);
}