package com.covid19.match.events;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class EmailNotificationEvent extends ApplicationEvent {
    private String email;
    private String reason;

    public EmailNotificationEvent(Object source, String email, String reason) {
        super(source);
        this.email = email;
        this.reason = reason;
    }
}
