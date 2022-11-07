package com.endava.wallet.domain;

import org.springframework.security.core.GrantedAuthority;

public enum Authority implements GrantedAuthority {
    USER("ROLE_USER"),
    ADMIN("ROLE_ADMIN");

    private final String authorityName;
    Authority(String authorityName) {
        this.authorityName = authorityName;
    }
    @Override
    public String getAuthority() {
        return authorityName;
    }
}
