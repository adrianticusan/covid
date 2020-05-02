package com.covid19.match.services;

import com.covid19.match.dtos.LocationDto;
import com.covid19.match.dtos.UserDisplayDto;
import com.covid19.match.dtos.UserDto;
import com.covid19.match.dtos.UserFindDto;
import com.covid19.match.entities.Location;
import com.covid19.match.entities.Settings;
import com.covid19.match.entities.User;
import com.covid19.match.mappers.LocationMapper;
import com.covid19.match.mappers.UserMapper;
import com.covid19.match.repositories.UserRepository;
import com.covid19.match.session.DistancePreference;
import com.covid19.match.utils.DistanceUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
public class VolunteerService {
    private UserMapper userMapper;
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private static final int DEFAULT_LIMIT = 10;

    @Autowired
    public VolunteerService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = UserMapper.INSTANCE;
    }

    public void saveLocationOnVolunteer(UUID userId, LocationDto locationDto) {
        Location location = LocationMapper.INSTANCE.locationDtoToLocation(locationDto);
        User user = findVolunteerById(userId.toString());
        user.setLocation(location);

        userRepository.save(user);
    }

    public void changeVolunteerPassword(String username, String newPassword) {
        User user = getUserOrException(username);
        user.setPassword(passwordEncoder.encode(newPassword));

        userRepository.save(user);
    }

    public void addUserToHelpedUsers(String loggedUserEmail, String userToBeHelpedId) {
        User loggedUser = getUserOrException(loggedUserEmail);
        User userToBeHelped = findVolunteerById(userToBeHelpedId);
        loggedUser.getUsers().add(userToBeHelped);
        userRepository.save(loggedUser);
    }

    public User findVolunteerById(String id) {
        return userRepository.findById(UUID.fromString(id)).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    public List<UserDto> findHelpedUsersInRange(UserFindDto userFindDto, DistancePreference distancePreference, int offset) {
        List<UserDto> userDtos = userMapper.usersToUserDtos(userRepository.findHelpedUsersInRange(
                userFindDto.getLocationId(), distancePreference.getFindDistanceInMeters(), userFindDto.getId(), offset));

        return calculateDistanceForUserDtos(userFindDto, userDtos);
    }

    public void removeUserFromHelpedUsers(String loggedUserEmail, String userToBeRemovedId) {
        User loggedUser = getUserOrException(loggedUserEmail);
        User userToBeHelped = findVolunteerById(userToBeRemovedId);
        loggedUser.getUsers().remove(userToBeHelped);
        userRepository.save(loggedUser);
    }

    public UserDto getVolunteerDto(String email) {
        User user = userRepository.findByEmailAndIsVolunteer(email, true)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found"));
        return userMapper.userToUserDto(user);
    }

    public List<UserDto> findUsersNeedHelpInRange(UserFindDto userFindDto, DistancePreference distancePreference, int offset) {
        List<UserDto> userDtos = userMapper.usersToUserDtos(userRepository.findUsersInRange(
                userFindDto.getLocationId(), distancePreference.getFindDistanceInMeters(), userFindDto.getId(), offset, DEFAULT_LIMIT));

        return calculateDistanceForUserDtos(userFindDto, userDtos);
    }

    public List<UserDisplayDto> findUsersNeedHelpInRange(UserFindDto userFindDto, DistancePreference distancePreference, int offset, int limit) {
        List<UserDto> userDtos = userMapper.usersToUserDtos(userRepository.findUsersInRange(
                userFindDto.getLocationId(), distancePreference.getFindDistanceInMeters(), userFindDto.getId(), offset, limit));

        return userMapper.userDtoToUserDisplayDto(calculateDistanceForUserDtos(userFindDto, userDtos));
    }

    public void updateDistancePreferences(UserFindDto userFindDto, DistancePreference distancePreference) {
        User volunteer = findVolunteerById(userFindDto.getId().toString());
        Settings settings = new Settings();
        settings.setDistanceUnit(distancePreference.getDistanceUnit());
        settings.setDistance(distancePreference.getFindDistance());
        volunteer.setSettings(settings);

        userRepository.save(volunteer);
    }

    public List<UUID> getHelpedUsers(UUID loggedUserId) {
        return userRepository.getHelpedUsers(loggedUserId);
    }

    public Integer countUsersInRange(UUID userId, UUID locationId, DistancePreference distancePreference) {
        return userRepository.countUsersInRange(userId, locationId, distancePreference.getFindDistanceInMeters());
    }


    private LocationDto getPointDtoFromUser(String loggedUserEmail) {
        return userRepository.findByEmail(loggedUserEmail)
                .map(User::getLocation)
                .map(LocationMapper.INSTANCE::locationToLocationDto)
                .orElse(null);
    }

    private List<UserDto> calculateDistanceForUserDtos(UserFindDto userFindDto, List<UserDto> userDtos) {
        if (CollectionUtils.isEmpty(userDtos)) {
            return Collections.emptyList();
        }

        LocationDto loggedUserPosition = getPointDtoFromUser(userFindDto.getEmail());
        userDtos.forEach(user -> user.getLocationDto().setDistance(
                DistanceUtils.getDistanceBetweenPoints(user.getLocationDto(), loggedUserPosition)));

        return userDtos;
    }

    private User getUserOrException(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("Username not found"));
    }
}

