package com.covid19.match.entities;

import lombok.Data;
import org.geolatte.geom.GeometryType;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.Point;

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
    @Column(name = "id", updatable = false, nullable = false, columnDefinition = "uuid")
    private UUID id;
    private String firstName;
    private String lastName;
    private String phoneNumber;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(columnDefinition = "geography(Point, 4326)")
    private Point position;

    private String state;
    private String country;
    private String streetAddress;
    private String zipCode;
    private String building;
    private String identityPhotoUrl;
    private Role role;
}
