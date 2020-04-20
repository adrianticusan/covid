package com.covid19.match.repositories;

import com.covid19.match.entities.DisabledEmail;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface DisabledEmailRepository extends CrudRepository<DisabledEmail, UUID> {
    int countByEmail(String email);
}