package com.covid19.match.repositories;

import com.covid19.match.entities.Contact;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface ContactRepository extends CrudRepository<Contact, UUID> {

    int countByEmail(String email);

}
