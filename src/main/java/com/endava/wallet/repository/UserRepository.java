package com.endava.wallet.repository;

import com.endava.wallet.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findUserByUsername(String username);
    boolean existsUserByUsername(String username);
}