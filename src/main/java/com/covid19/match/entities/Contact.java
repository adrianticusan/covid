package com.covid19.match.entities;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Data
@Table(name = "contact")
public class Contact {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id", updatable = false, nullable = false, columnDefinition = "uuid")
    @Type(type = "pg-uuid")
    private UUID id;

    private String name;

    @Column(name = "email")
    private String email;

    private String notes;

    @Column(columnDefinition = "boolean default false")
    private boolean status;
}
