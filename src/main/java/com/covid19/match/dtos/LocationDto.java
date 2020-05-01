package com.covid19.match.dtos;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class LocationDto {
    @NotNull
    private String country;

    @NotBlank
    private String state;

    @NotBlank
    private String locality;

    @NotBlank
    private String streetAddress;

    private String streetNumber;

    @NotNull
    private Double latitude;

    @NotNull
    private Double longitude;

    private Double distance;
}
