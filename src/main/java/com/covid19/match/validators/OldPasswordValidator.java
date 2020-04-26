package com.covid19.match.validators;

import com.covid19.match.services.UserService;
import com.covid19.match.utils.UserHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class OldPasswordValidator implements ConstraintValidator<OldPasswordConstraint, String> {

    private UserService userService;
    private PasswordEncoder passwordEncoder;

    @Autowired
    OldPasswordValidator(UserService securityService,
                         PasswordEncoder passwordEncoder) {
        this.userService = securityService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void initialize(OldPasswordConstraint oldPasswordConstraint) {
    }

    @Override
    public boolean isValid(String oldPassword,
                           ConstraintValidatorContext cxt) {
        String loggedUserEmail = UserHelper.getLoggedUserDto(SecurityContextHolder.getContext());
        String databasePassword = userService.getUserDto(loggedUserEmail).getPassword();

        return passwordEncoder.matches(oldPassword, databasePassword);
    }
}