package com.covid19.match.entities;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@Table(name = "disabled_emails")
public class DisabledEmail {
    @Id
    String email;

    @Column(name = "is_disabled", nullable = false, columnDefinition = "boolean default false")
    boolean disabled;

    private String reason;
}
