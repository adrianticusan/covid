package com.covid19.match.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.locationtech.jts.geom.Point;

import javax.persistence.*;
import java.util.Set;
import java.util.UUID;

@Entity
@Data
@Table(name = "users")
@EqualsAndHashCode(of = "id")
public class User {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id", updatable = false, nullable = false, columnDefinition = "uuid")
    @Type(type="pg-uuid")
    private UUID id;
    private String firstName;
    private String lastName;
    private String phoneNumber;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    private String identityPhotoUrl;
    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(name = "is_volunteer", nullable = false, columnDefinition = "boolean default false")
    private boolean isVolunteer;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @JoinColumn(name = "location_id", referencedColumnName = "id")
    private Location location;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "volunteer_to_users",
            joinColumns = @JoinColumn(name = "volunteer_id", nullable = true),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<User> users;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @JoinColumn(name = "settings_id", referencedColumnName = "id")
    private Settings settings;
}
