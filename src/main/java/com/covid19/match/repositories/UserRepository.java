package com.covid19.match.repositories;

import com.covid19.match.entities.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends CrudRepository<User, UUID> {
    Optional<User> findByEmail(String email);

    @Query(value="SELECT * FROM users WHERE ST_DWithin(position ,CAST(ST_SetSRID( ST_Point( ?1, ?2), 4326) AS geography), ?3);", nativeQuery = true)
    List<User> findUsersInRange(double longitude, double latitude, double userRangeInMeters);

    int countByEmail(String email);
}
