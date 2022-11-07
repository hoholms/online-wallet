package com.endava.wallet.service;

import com.endava.wallet.domain.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface UserService {
    User loadUserByUsername(String username) throws UsernameNotFoundException;
}
