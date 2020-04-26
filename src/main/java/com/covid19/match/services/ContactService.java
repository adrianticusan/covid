package com.covid19.match.services;

import com.covid19.match.dtos.ContactDto;
import com.covid19.match.entities.Contact;
import com.covid19.match.events.ContactMessageEvent;
import com.covid19.match.events.UserCreatedEvent;
import com.covid19.match.mappers.ContactMapper;
import com.covid19.match.repositories.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
public class ContactService {
    private ContactRepository contactRepository;
    private ContactMapper contactMapper;
    private ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    public ContactService(ContactRepository contactRepository, ApplicationEventPublisher applicationEventPublisher) {
        this.contactRepository = contactRepository;
        this.applicationEventPublisher = applicationEventPublisher;
        this.contactMapper = ContactMapper.INSTANCE;
    }

    public void saveMessage(ContactDto contactDto) {
        Contact contact = contactRepository.save(contactMapper.contactDtoToContact(contactDto));
        ContactDto createdContactDto = contactMapper.contactToContactDto(contact);
        applicationEventPublisher.publishEvent(new ContactMessageEvent(this, createdContactDto));
    }

    public boolean checkIfUserCanSendMessages(String email) {
        return contactRepository.countByEmail(email) <= 2;
    }
}
