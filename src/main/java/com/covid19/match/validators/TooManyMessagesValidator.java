package com.covid19.match.validators;

import com.covid19.match.services.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
public class TooManyMessagesValidator implements ConstraintValidator<TooManyMessagesConstraint, String> {

    private ContactService contactService;

    @Autowired
    public TooManyMessagesValidator(ContactService contactService) {
        this.contactService = contactService;
    }

    @Override
    public boolean isValid(String email,
                           ConstraintValidatorContext cxt) {
        return contactService.checkIfUserCanSendMessages(email);
    }
}
