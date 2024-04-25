package com.online.wallet.service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.data.domain.Sort;
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

  private final UserRepository  userRepository;
  private final PasswordEncoder passwordEncoder;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    return userRepository
        .findUserByUsername(username)
        .orElseThrow(() -> new UsernameNotFoundException(String.format("User with username \"%s\" not found",
            username)));
  }

  public void save(User user) {
    userRepository.save(user);
  }

  public boolean add(User user) {
    if (userRepository.existsUserByUsername(user.getUsername())) {
      return false;
    }

    user.setEnabled(false);
    user.setAuthority(Collections.singleton(Authority.USER));
    user.setPassword(passwordEncoder.encode(user.getPassword()));

    userRepository.save(user);

    return true;
  }

  public void updateUser(Long userId, String username, Boolean enabled, Map<String, String> form) {
    User user = findUserById(userId);

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
  }

  public User findUserById(Long id) {
    return userRepository
        .findById(id)
        .orElseThrow(() -> new UserNotFoundException("User with id: " + id + " not found"));
  }

  public void deleteUserById(Long userID) {
    findUserById(userID);
    userRepository.deleteById(userID);
  }

  public List<User> findAllUsers() {
    return userRepository.findAll(Sort.by(Sort.Order.by("id")));
  }

  public boolean existsUserByUsername(String username) {
    return userRepository.existsUserByUsername(username);
  }

}