package com.covid19.match.configs.security;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.UUID;

public class CustomUser extends User {

    private final UUID userID;

    public CustomUser(String username, String password, Collection<? extends GrantedAuthority> authorities, UUID userID) {
        super(username, password, authorities);
        this.userID = userID;
    }

    public UUID getUserID() {
        return userID;
    }
}
