package com.endava.wallet.service;

import com.endava.wallet.entity.Authority;
import com.endava.wallet.entity.User;
import com.endava.wallet.exception.ApiRequestException;
import com.endava.wallet.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findUserByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    public List<User> findAllUsers() {

        return this.userRepository.findAll();
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

    public void add(@RequestParam String username,
                    @RequestParam Map<String, String> form,
                    User user) {

        user.setUsername(username);
        Set<String> authorities = Arrays.stream(Authority.values())
                .map(Authority::name)
                .collect(Collectors.toSet());

        user.getAuthority().clear();

        for (String key : form.keySet()) {
            if (authorities.contains(key)) {
                user.getAuthority().add(Authority.valueOf(key));
            }
        }

        userRepository.save(user);
    }

    public User findUserById(Long id) {
        if (userRepository.findUserById(id) == null) {
            throw new ApiRequestException("User with id: " + id + " not found");
        }
        return userRepository.findUserById(id);
    }

    public Boolean existsUserByUsername(String username) {
        return userRepository.existsUserByUsername(username);
    }

    public void deleteUserById(Long userID) {
        findUserById(userID);
        userRepository.deleteById(userID);
    }

    public void save(User user) {
        userRepository.save(user);
    }
}