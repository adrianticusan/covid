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
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.LocaleResolver;

import java.util.List;
import java.util.Locale;

@Component
@Slf4j
public class MailingService {
    private AmazonSimpleEmailService amazonSimpleEmailService;
    private MailConfiguration mailConfiguration;
    private DisabledEmailRepository disabledEmailRepository;
    private MessageSource messageSource;

    @Autowired
    public MailingService(AmazonSimpleEmailService amazonSimpleEmailService,
                          MailConfiguration mailConfiguration,
                          DisabledEmailRepository disabledEmailRepository,
                          MessageSource messageSource) {
        this.amazonSimpleEmailService = amazonSimpleEmailService;
        this.mailConfiguration = mailConfiguration;
        this.disabledEmailRepository = disabledEmailRepository;
        this.messageSource = messageSource;
    }

    public void sendRegisterMail(MailingTypes type, MailingDto<UserDto> userDto, String originalPassword) {
        String email = userDto.getExtraInformation().getEmail();

        if (isEmailSendingBlocked(email)) {
            log.info("Could not send email, email {} is blocked", email);
            return;
        }

        SendEmailRequest sendEmailRequest = new SendEmailRequest();
        sendEmailRequest.setDestination(new Destination(List.of(userDto.getExtraInformation().getEmail())));
        sendEmailRequest.setSource(mailConfiguration.getMailFrom());

        sendEmailRequest.setMessage(getRegistrationMessage(type, originalPassword));
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
        return disabledEmailRepository.countByEmail(email) > 0;
    }

    private Message getRegistrationMessage(MailingTypes type, String originalPassword) {
        Message message = new Message();
        message.setSubject(new Content(messageSource.getMessage("mail.registration.subject", new Object[]{}, Locale.ENGLISH)));

        if (type.equals(MailingTypes.REGISTER_VOLUNTEER)) {
            message.setBody(new Body(new Content(messageSource.getMessage("mail.registration.body.volunteer", new Object[]{}, Locale.ENGLISH))));
            return message;
        }

        message.setBody(new Body(new Content(messageSource.getMessage("mail.registration.body.user", new Object[]{originalPassword}, Locale.ENGLISH))));

        return message;
    }
}
