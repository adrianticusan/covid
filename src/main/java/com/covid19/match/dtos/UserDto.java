package com.covid19.match.dtos;

import com.covid19.match.entities.Role;
import com.covid19.match.enums.DistanceUnit;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

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
    private LocationDto locationDto;
    private String identityPhotoUrl;
    private Role role;
    private boolean isVolunteer;
    private List<UserDto> users;
    MultipartFile uploadedFile;
    private Integer findDistance;
    private DistanceUnit distanceUnit;

    public String getAddress() {
        return String.join(" ", locationDto.getCountry(), locationDto.getState(), locationDto.getLocality(),
                locationDto.getStreetAddress(), locationDto.getStreetNumber());
    }
}
