package com.covid19.match.services;

import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.model.*;
import com.covid19.match.dtos.MailingDto;
import com.covid19.match.dtos.UserDto;
import com.covid19.match.enums.MailingTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MailingService {
    private AmazonSimpleEmailService amazonSimpleEmailService;
    private String mailSource;


    @Autowired
    public MailingService(AmazonSimpleEmailService amazonSimpleEmailService) {
        this.amazonSimpleEmailService = amazonSimpleEmailService;
    }

    public void sendRegisterMail(MailingTypes type, MailingDto<UserDto> userDto) {
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
