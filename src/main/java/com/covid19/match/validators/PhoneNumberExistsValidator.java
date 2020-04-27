package com.covid19.match.validators;

import com.covid19.match.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PhoneNumberExistsValidator  implements ConstraintValidator<PhoneNumberExistsConstraint, String> {
    private UserService userService;

    @Autowired
    public PhoneNumberExistsValidator(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean isValid(String phoneNumber,
                           ConstraintValidatorContext cxt) {
        return !userService.checkIfPhoneNumberExists(phoneNumber);
    }
}
