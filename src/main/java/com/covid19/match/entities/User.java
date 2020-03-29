package com.covid19.match.entities;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.geo.Point;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Data
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;
    private String firstName;
    private String lastName;
    private String phoneNumber;

    @Column(columnDefinition = "geometry(Point)")
    private Point geom;

}
