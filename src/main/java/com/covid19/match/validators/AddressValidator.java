package com.covid19.match.validators;

import com.covid19.match.dtos.UserRegisterDto;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.MessageSource;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Locale;

public class AddressValidator implements Validator {
    private MessageSource messageSource;

    public AddressValidator(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return UserRegisterDto.class.equals(clazz);
    }

    @Override
    public void validate(Object obj, Errors e) {
        UserRegisterDto userRegisterDto = (UserRegisterDto) obj;
        if (StringUtils.isAnyBlank(userRegisterDto.getCountry(), userRegisterDto.getState(),
                userRegisterDto.getLocality(), userRegisterDto.getStreetAddress())) {
            String invalidAddress = messageSource.getMessage("messages.errors.register.invalid.address", new Object[]{}, Locale.ENGLISH);
            e.rejectValue("address", "invalid.address", invalidAddress);
        }
    }

}
