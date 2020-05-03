package com.covid19.match.events;

import com.covid19.match.dtos.ContactDto;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class ContactMessageEvent extends ApplicationEvent {
    private ContactDto contactDto;

    public ContactMessageEvent(Object source, ContactDto contactDto) {
        super(source);
        this.contactDto = contactDto;
    }
}
