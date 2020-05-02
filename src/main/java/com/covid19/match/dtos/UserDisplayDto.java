package com.covid19.match.dtos;

import lombok.Data;

@Data
public class UserDisplayDto {
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private LocationDto locationDto;
}
