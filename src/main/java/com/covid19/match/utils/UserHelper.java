package com.covid19.match.utils;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;

public class UserHelper {
    public static String getLoggedUserEmail(SecurityContext context) {
        Authentication authentication = context.getAuthentication();
        if ((authentication instanceof AnonymousAuthenticationToken)) {
            //ERROR
            System.out.println("mitic");
        }
        return authentication.getName();
    }
}
