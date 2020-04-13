package com.covid19.match.listeners;

import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.model.*;
import com.covid19.match.configs.security.SecurityService;
import com.covid19.match.dtos.UserDto;
import com.covid19.match.events.UserCreatedEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserCreatedListener implements ApplicationListener<UserCreatedEvent> {
    private SecurityService securityService;
    private AmazonSimpleEmailService amazonSimpleEmailService;

    @Autowired
    public UserCreatedListener(SecurityService securityService, AmazonSimpleEmailService amazonSimpleEmailService) {
        this.securityService = securityService;
        this.amazonSimpleEmailService = amazonSimpleEmailService;
    }

    @Override
    public void onApplicationEvent(UserCreatedEvent userCreatedEvent) {
        UserDto userDto = userCreatedEvent.getUserDto();
        securityService.autologin(userDto.getEmail(), userCreatedEvent.getOriginalPassword());
        SendEmailRequest sendEmailRequest = new SendEmailRequest();
        sendEmailRequest.setDestination(new Destination(List.of("adi.ticusan99@gmail.com")));
        sendEmailRequest.setSource("mail@usacovid19match.com");
        Message message = new Message();
        message.setBody(new Body(new Content("test")));
        sendEmailRequest.setMessage(message);
        message.setSubject(new Content("Registration successful"));
        amazonSimpleEmailService.sendEmail(sendEmailRequest);
    }
}
