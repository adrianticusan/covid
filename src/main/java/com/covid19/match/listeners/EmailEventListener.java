package com.covid19.match.listeners;

import com.covid19.match.events.EmailNotificationEvent;
import com.covid19.match.services.MailingService;
import org.springframework.context.ApplicationListener;

public class EmailEventListener implements ApplicationListener<EmailNotificationEvent> {
    private MailingService mailingService;

    @Override
    public void onApplicationEvent(EmailNotificationEvent event) {
        mailingService.blockEmail(event.getEmail(), event.getReason());
    }
}


