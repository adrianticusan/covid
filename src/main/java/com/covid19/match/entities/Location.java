package com.covid19.match.entities;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.locationtech.jts.geom.Point;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Data
@Table(name = "locations")
public class Location {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id", updatable = false, nullable = false, columnDefinition = "uuid")
    @Type(type="pg-uuid")
    private UUID id;

    private String country;
    private String state;
    private String locality;
    private String streetAddress;
    private String streetNumber;

    @Column(columnDefinition = "geography(Point, 4326)")
    private Point currentPosition;

    @OneToOne(mappedBy = "location")
    private User user;
}
