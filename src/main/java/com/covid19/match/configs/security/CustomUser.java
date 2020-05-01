package com.covid19.match.configs.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.UUID;

public class CustomUser extends User {

    private final UUID userID;
    private final UUID locationID;


    public CustomUser(String username, String password, Collection<? extends GrantedAuthority> authorities, UUID userID, UUID locationID) {
        super(username, password, authorities);
        this.userID = userID;
        this.locationID = locationID;
    }

    public UUID getUserID() {
        return userID;
    }

    public UUID getLocationID() {
        return locationID;
    }
}
