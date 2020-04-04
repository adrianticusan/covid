package com.covid19.match.dtos;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class UserRegisterDto {
    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @NotBlank
    private String phoneNumber;

    @NotBlank
    private String email;

    @NotBlank
    private String password;

    @NotBlank
    private String state;

    @NotBlank
    private String country;

    @NotBlank
    private String streetAddress;

    @NotBlank
    private String zipCode;

    @NotBlank
    private String building;
}
