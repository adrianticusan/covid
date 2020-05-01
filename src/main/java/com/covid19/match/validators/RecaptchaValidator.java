package com.covid19.match.validators;

import com.covid19.match.external.google.api.GoogleRecaptchaApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
public class RecaptchaValidator
        implements ConstraintValidator<RecaptchaConstraint, String> {

    private GoogleRecaptchaApi googleRecaptchaApi;

    @Autowired
    RecaptchaValidator(GoogleRecaptchaApi googleRecaptchaApi) {
        this.googleRecaptchaApi = googleRecaptchaApi;
    }

    @Override
    public void initialize(RecaptchaConstraint recaptchaConstraint) {
    }

    @Override
    public boolean isValid(String token,
                           ConstraintValidatorContext cxt) {
        return googleRecaptchaApi.validateCaptcha(token);
    }

}