package com.covid19.match.services;

import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.model.*;
import com.covid19.match.configs.mail.MailConfiguration;
import com.covid19.match.dtos.MailingDto;
import com.covid19.match.dtos.UserDto;
import com.covid19.match.entities.DisabledEmail;
import com.covid19.match.enums.MailingTypes;
import com.covid19.match.repositories.DisabledEmailRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class MailingService {
    private AmazonSimpleEmailService amazonSimpleEmailService;
    private MailConfiguration mailConfiguration;
    private DisabledEmailRepository disabledEmailRepository;

    @Autowired
    public MailingService(AmazonSimpleEmailService amazonSimpleEmailService,
                          MailConfiguration mailConfiguration,
                          DisabledEmailRepository disabledEmailRepository) {
        this.amazonSimpleEmailService = amazonSimpleEmailService;
        this.mailConfiguration = mailConfiguration;
        this.disabledEmailRepository = disabledEmailRepository;
    }

    public void sendRegisterMail(MailingTypes type, MailingDto<UserDto> userDto) {
        String email = userDto.getExtraInformation().getEmail();

        if (isEmailSendingBlocked(email)) {
            log.info("Could not send email, email {} is blocked", email);
            return;
        }

        SendEmailRequest sendEmailRequest = new SendEmailRequest();
        sendEmailRequest.setDestination(new Destination(List.of("bounce@simulator.amazonses.com")));
        sendEmailRequest.setSource(mailConfiguration.getMailFrom());
        Message message = new Message();
        message.setBody(new Body(new Content("test")));
        sendEmailRequest.setMessage(message);
        message.setSubject(new Content("Registration successful"));
        amazonSimpleEmailService.sendEmail(sendEmailRequest);
    }

    public void blockEmail(String email, String reason) {
        if (isEmailSendingBlocked(email)) {
            return;
        }

        DisabledEmail disabledEmail = new DisabledEmail();
        disabledEmail.setDisabled(true);
        disabledEmail.setReason(reason);

        disabledEmailRepository.save(disabledEmail);
    }


    public boolean isEmailSendingBlocked(String email) {
        return disabledEmailRepository.countByEmail(email) > 1;
    }
}
