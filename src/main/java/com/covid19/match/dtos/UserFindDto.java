package com.covid19.match.dtos;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class UserFindDto {
    private UUID id;
    private String email;
    private String firstName;
    private String lastName;

    public String getName() {
        return firstName + " " + lastName;
    }
}

