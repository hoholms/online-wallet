package com.online.wallet.service;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.online.wallet.exception.UserNotFoundException;
import com.online.wallet.model.Authority;
import com.online.wallet.model.User;
import com.online.wallet.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

  private static final Logger          logger = LoggerFactory.getLogger(UserService.class);
  private final        UserRepository  userRepository;
  private final        PasswordEncoder passwordEncoder;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    logger.info("Loading user by username: {}", username);
    return userRepository.findUserByUsername(username).orElseThrow(() -> {
      logger.error("User with username \"{}\" not found", username);
      return new UsernameNotFoundException(String.format("User with username \"%s\" not found", username));
    });
  }

  public void save(User user) {
    userRepository.save(user);
    logger.info("User saved with id: {}", user.getId());
  }

  public boolean add(User user) {
    if (userRepository.existsUserByUsername(user.getUsername())) {
      logger.warn("User with username \"{}\" already exists", user.getUsername());
      return false;
    }

    user.setEnabled(false);
    user.setAuthority(Collections.singleton(Authority.USER));
    user.setPassword(passwordEncoder.encode(user.getPassword()));

    userRepository.save(user);
    logger.info("User added with username: {}", user.getUsername());

    return true;
  }

  public void updateUser(Long userId, String username, Boolean enabled, Map<String, String> form) {
    User user = findUserById(userId);
    logger.info("Updating user with id: {}", userId);

    user.setUsername(username);
    user.setEnabled(enabled);

    Set<String> authorities = Arrays.stream(Authority.values()).map(Authority::name).collect(Collectors.toSet());

    user.getAuthority().clear();

    for (String key : form.keySet()) {
      if (authorities.contains(key)) {
        user.getAuthority().add(Authority.valueOf(key));
      }
    }

    userRepository.save(user);
    logger.info("Updated user with id: {}", userId);
  }

  public User findUserById(Long id) {
    logger.info("Finding user by id: {}", id);
    return userRepository.findById(id).orElseThrow(() -> {
      logger.error("User with id: {} not found", id);
      return new UserNotFoundException("User with id: " + id + " not found");
    });
  }

  public void deleteUserById(User user, Long userID) {
    if (user != null && !user.getId().equals(userID)) {
      logger.info("Deleting user with id: {}", userID);
      findUserById(userID);
      userRepository.deleteById(userID);
      logger.info("Deleted user with id: {}", userID);
    }
  }

  public Page<User> findAllUsers(final Pageable pageable) {
    logger.info("Fetching all users");
    return userRepository.findAll(pageable);
  }

  public boolean existsUserByUsername(String username) {
    boolean exists = userRepository.existsUserByUsername(username);
    logger.debug("Check if user exists by username \"{}\": {}", username, exists);
    return exists;
  }

}
