package com.covid19.match.services;

import com.amazonaws.services.simpleemail.model.Message;
import com.covid19.match.configs.mail.MailConfiguration;
import com.covid19.match.dtos.ContactDto;
import com.covid19.match.dtos.MailingDto;
import com.covid19.match.dtos.UserDto;
import com.covid19.match.entities.DisabledEmail;
import com.covid19.match.enums.MailingTypes;
import com.covid19.match.external.pepipost.PepipostService;
import com.covid19.match.repositories.DisabledEmailRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
@Slf4j
public class MailingService {
    private MailConfiguration mailConfiguration;
    private MessageSource messageSource;
    private PepipostService pepipostService;
    private DisabledEmailRepository disabledEmailRepository;

    @Autowired
    public MailingService(MailConfiguration mailConfiguration,
                          DisabledEmailRepository disabledEmailRepository,
                          PepipostService pepipostService,
                          MessageSource messageSource) {
        this.mailConfiguration = mailConfiguration;
        this.disabledEmailRepository = disabledEmailRepository;
        this.pepipostService = pepipostService;
        this.messageSource = messageSource;
    }

    public void sendRegisterMail(MailingTypes type, MailingDto<UserDto> userDto, String originalPassword) {
        String email = userDto.getExtraInformation().getEmail();

        if (isEmailSendingBlocked(email)) {
            log.info("Could not send email, email {} is blocked", email);
            return;
        }

        String subject = messageSource.getMessage("mail.registration.subject", new Object[]{originalPassword}, Locale.ENGLISH);
        String content = getRegistrationMessage(type, originalPassword);
        String fromName = messageSource.getMessage("mail.from.name", new Object[]{}, Locale.ENGLISH);
        pepipostService.sendEmail(mailConfiguration.getMailFrom(), userDto.getExtraInformation().getEmail(), subject, content, fromName);
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

    public void  sendContactUsMail(MailingTypes type, MailingDto<ContactDto> contactDto) {
        String email = contactDto.getExtraInformation().getEmail();

        if (isEmailSendingBlocked(email)) {
            log.info("Could not send email, email {} is blocked", email);
            return;
        }

        String content = contactDto.getExtraInformation().getNotes();
        String subject = messageSource.getMessage("mail.contact.us.subject", new Object[]{}, Locale.ENGLISH);
        String fromName = messageSource.getMessage("mail.from.name", new Object[]{}, Locale.ENGLISH);
        String toEmail = messageSource.getMessage("mail.contact.us.to.email", new Object[]{}, Locale.ENGLISH);
        pepipostService.sendEmail(mailConfiguration.getMailFrom(), toEmail, subject, content, fromName);
    }


    public boolean isEmailSendingBlocked(String email) {
        return disabledEmailRepository.countByEmail(email) > 0;
    }

    private String getRegistrationMessage(MailingTypes type, String originalPassword) {
        Message message = new Message();


        if (type.equals(MailingTypes.REGISTER_VOLUNTEER)) {
            return messageSource.getMessage("mail.registration.body.volunteer", new Object[]{}, Locale.ENGLISH);
        }

        return messageSource.getMessage("mail.registration.body.user", new Object[]{originalPassword}, Locale.ENGLISH);
    }
}
