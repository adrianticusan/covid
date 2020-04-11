package com.covid19.match.dtos;

import lombok.Data;

@Data
public class PointDto {
    double latitude;

    double longitude;

    double distanceInKm;
}
