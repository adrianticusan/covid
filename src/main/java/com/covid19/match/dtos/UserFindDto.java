package com.covid19.match.dtos;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class UserFindDto {
    private UUID id;
    private UUID locationId;
    private String email;
}

