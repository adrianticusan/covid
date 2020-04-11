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

    @Query(value="SELECT * FROM users, (SELECT ST_SetSRID( ST_Point( ?1, ?2), 4326)) AS t(x)  " +
            "WHERE ST_DWithin(t.x, position, ?3) ORDER  BY ST_Distance(t.x, position) OFFSET ?4 LIMIT 5;", nativeQuery = true)
    List<User> findSortedUsersInRange(double longitude, double latitude, double userRangeInMeters, int offset);

    @Query(value="SELECT COUNT(*) FROM users, (SELECT ST_SetSRID( ST_Point( ?1, ?2), 4326)) AS t(x)  " +
            "WHERE ST_DWithin(t.x, position, ?3);", nativeQuery = true)
    Integer countUsersInRange(double longitude, double latitude, double userRangeInMeters);
}
