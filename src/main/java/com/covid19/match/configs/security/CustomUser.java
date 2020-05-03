package com.covid19.match.configs.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.UUID;

public class CustomUser extends User {

    private final UUID userID;
    private final String firstName;
    private final String lastName;


    public CustomUser(String username, String password, Collection<? extends GrantedAuthority> authorities, UUID userID, String firstName, String lastName) {
        super(username, password, authorities);
        this.userID = userID;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public UUID getUserID() {
        return userID;
    }


    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
}
