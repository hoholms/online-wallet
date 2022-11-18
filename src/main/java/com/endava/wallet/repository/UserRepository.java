package com.endava.wallet.repository;

import com.endava.wallet.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findUserByUsername(String username);

    User findUserById(Long id);

    boolean existsUserByUsername(String username);


}