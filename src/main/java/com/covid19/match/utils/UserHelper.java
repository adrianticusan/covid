package com.covid19.match.utils;

import org.springframework.security.core.context.SecurityContext;

public class UserHelper {
    public static String getLoggedUserEmail(SecurityContext context) {
        return context.getAuthentication().getName();
    }
}
