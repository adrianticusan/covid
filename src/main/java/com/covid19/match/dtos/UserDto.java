package com.covid19.match.dtos;
import com.covid19.match.entities.Role;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class UserDto {
    private UUID id;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
    private String password;
    private PointDto positionDto;
    private String country;
    private String state;
    private String locality;
    private String streetAddress;
    private String streetNumber;
    private String identityPhotoUrl;
    private Role role;
    private boolean isVolunteer;
    private List<UserDto> users;
}
