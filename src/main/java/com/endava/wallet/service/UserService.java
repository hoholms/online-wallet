package com.endava.wallet.service;

import com.endava.wallet.entity.Authority;
import com.endava.wallet.entity.User;
import com.endava.wallet.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findUserByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    public List<User> findAllUsers() {
        return this.userRepository.findAll();
    }

    public void add(User user) {
        userRepository.save(user);
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
        User user;
        if (userRepository.findById(id).isPresent()){
            user = userRepository.findById(id).get();
            return user;
        }
        else return null;
    }

    public Boolean existsUserByUsername(String username) {
        return userRepository.existsUserByUsername(username);
    }

    public void deleteUserById(Long userID) {
        userRepository.deleteById(userID);
    }


}