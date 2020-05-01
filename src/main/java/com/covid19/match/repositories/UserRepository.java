package com.covid19.match.repositories;

import com.covid19.match.entities.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends CrudRepository<User, UUID> {
    Optional<User> findByEmail(String email);

    int countByEmail(String email);

    @Query(value="SELECT * FROM users u JOIN volunteer_to_users vtu on u.id = vtu.user_id " +
            "JOIN locations ul ON ul.id = u.location_id, " +
            "(SELECT current_position from locations where id = ?1) AS t(x)" +
            "WHERE u.is_volunteer = false AND (ST_DWithin(t.x, ul.current_position, ?2)" +
            " AND (vtu.volunteer_id = ?3) ) " +
            "ORDER  BY ST_Distance(t.x, ul.current_position) OFFSET ?4 LIMIT 5;", nativeQuery = true)
    List<User> findHelpedUsersInRange(UUID id, double userRangeInMeters, UUID userId, int offset);

    @Query(value="SELECT * FROM users u " +
            "JOIN locations ul ON ul.id = u.location_id, " +
            "(SELECT current_position from locations where id = ?1) AS t(x)" +
            "WHERE (ST_DWithin(t.x, ul.current_position, ?2)" +
            " AND u.is_volunteer = false and u.id not in (select user_id from volunteer_to_users where volunteer_id = ?3)) " +
            "ORDER  BY ST_Distance(t.x, ul.current_position) OFFSET ?4 LIMIT 5;", nativeQuery = true)
    List<User> findUsersInRange(UUID locationId, double userRangeInMeters, UUID id, int offset);

    @Query(value="SELECT COUNT(*) FROM users u" +
            " JOIN locations ul ON ul.id = u.location_id, " +
            " (SELECT current_position from locations where id = ?2) AS t(x)  " +
            "WHERE (ST_DWithin(t.x, ul.current_position, ?3)" +
            " AND  u.is_volunteer = false and u.id not in (select user_id from volunteer_to_users where volunteer_id = ?1));", nativeQuery = true)
    Integer countUsersInRange(UUID userId, UUID locationId, double userRangeInMeters);

    @Query(value="SELECT cast(user_id as varchar) from volunteer_to_users where volunteer_id = ?1", nativeQuery = true)
    List<UUID> getHelpedUsers(UUID id);
}
