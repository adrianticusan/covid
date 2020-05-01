package com.covid19.match.entities;

import com.covid19.match.enums.DistanceUnit;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Data
@Table(name = "settings")
public class Settings {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id", updatable = false, nullable = false, columnDefinition = "uuid")
    @Type(type="pg-uuid")
    private UUID id;

    private int distance;

    @Enumerated(EnumType.STRING)
    private DistanceUnit distanceUnit;

    @OneToOne(mappedBy = "settings")
    private User user;
}
