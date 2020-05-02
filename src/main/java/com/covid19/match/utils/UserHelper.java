package com.covid19.match.utils;

import com.covid19.match.configs.security.CustomUser;
import com.covid19.match.dtos.UserFindDto;
import org.springframework.security.core.context.SecurityContext;

public class UserHelper {
    public static UserFindDto getLoggedUserDto(SecurityContext context) {
        CustomUser customUser = (CustomUser) context.getAuthentication().getPrincipal();
        return UserFindDto.builder()
                .email(context.getAuthentication().getName())
                .id(customUser.getUserID())
                .build();
    }

    public static String getLoggedUserEmail(SecurityContext context) {
        CustomUser customUser = (CustomUser) context.getAuthentication().getPrincipal();
        return customUser.getUsername();
    }
}
