package com.covid19.match.repositories;

import com.covid19.match.entities.Location;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface LocationRepository extends CrudRepository<Location, UUID> {
    @Query(value = "SELECT l.* FROM locations l" +
            " JOIN users u ON u.location_id = l.id " +
            "WHERE u.id = ?1", nativeQuery = true)
    Location findLocationByUserId(UUID userId);
}