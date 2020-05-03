package com.covid19.match.listeners;

import com.covid19.match.dtos.ContactDto;
import com.covid19.match.dtos.MailingDto;
import com.covid19.match.enums.MailingTypes;
import com.covid19.match.events.ContactMessageEvent;
import com.covid19.match.services.MailingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Component
public class ContactMessageListener implements ApplicationListener<ContactMessageEvent> {
    private MailingService mailingService;

    @Autowired
    public ContactMessageListener(MailingService mailingService) {
        this.mailingService = mailingService;
    }

    @Override
    public void onApplicationEvent(ContactMessageEvent contactMessageEvent) {
        ContactDto contactDto = contactMessageEvent.getContactDto();

        CompletableFuture.runAsync(() -> {
            mailingService.sendContactUsMail(MailingTypes.CONTACT_US, new MailingDto<>(contactDto));
        });
    }
}
