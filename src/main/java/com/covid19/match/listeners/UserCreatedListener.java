package com.covid19.match.listeners;

import com.covid19.match.amazon.services.UploadService;
import com.covid19.match.configs.security.SecurityService;
import com.covid19.match.dtos.MailingDto;
import com.covid19.match.dtos.UserDto;
import com.covid19.match.enums.MailingTypes;
import com.covid19.match.events.UserCreatedEvent;
import com.covid19.match.services.MailingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Component
public class UserCreatedListener implements ApplicationListener<UserCreatedEvent> {
    private SecurityService securityService;
    private MailingService mailingService;
    private UploadService uploadService;

    @Autowired
    public UserCreatedListener(SecurityService securityService, MailingService mailingService) {
        this.securityService = securityService;
        this.mailingService = mailingService;
    }

    @Override
    public void onApplicationEvent(UserCreatedEvent userCreatedEvent) {
        UserDto userDto = userCreatedEvent.getUserDto();

        if (userDto.isVolunteer()) {
            securityService.autologin(userDto.getEmail(), userCreatedEvent.getOriginalPassword());
            CompletableFuture.runAsync(() -> {
                mailingService.sendRegisterMail(MailingTypes.REGISTER_VOLUNTEER, new MailingDto<>(userDto));
            });
            return;
        }

        mailingService.sendRegisterMail(MailingTypes.REGISTER_USER, new MailingDto<>(userDto));
    }
}
