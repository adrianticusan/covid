package com.covid19.match.dtos;

import lombok.Data;

import javax.validation.constraints.*;

@Data
public class UserRegisterDto {
    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @NotBlank
    @Pattern(regexp="(^$|[0-9]{10})")
    private String phoneNumber;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Min(value = 6)
    private String password;

    @NotNull
    private String country;

    @NotBlank
    private String state;

    @NotBlank
    private String locality;

    @NotBlank
    private String streetAddress;

    private String zipCode;

    private String streetNumber;

    @NotNull
    private Double latitude;

    @NotNull
    private Double longitude;
}
