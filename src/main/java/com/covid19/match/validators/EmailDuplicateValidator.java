package com.covid19.match.validators;

import com.covid19.match.google.api.GoogleRecaptchaApi;
import com.covid19.match.services.MailingService;
import com.covid19.match.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
public class EmailDuplicateValidator implements ConstraintValidator<EmailDuplicateConstraint, String> {

    private UserService userService;

    @Autowired
    EmailDuplicateValidator(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void initialize(EmailDuplicateConstraint recaptchaConstraint) {
    }

    @Override
    public boolean isValid(String email,
                           ConstraintValidatorContext cxt) {
        return !userService.isEmailAlreadyUsed(email);
    }
}