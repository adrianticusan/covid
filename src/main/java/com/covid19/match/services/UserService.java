package com.covid19.match.services;

import com.covid19.match.dtos.*;
import com.covid19.match.entities.Location;
import com.covid19.match.entities.User;
import com.covid19.match.events.UserCreatedEvent;
import com.covid19.match.external.amazon.services.UploadService;
import com.covid19.match.mappers.LocationMapper;
import com.covid19.match.mappers.UserMapper;
import com.covid19.match.repositories.UserRepository;
import com.covid19.match.session.DistancePreference;
import com.covid19.match.utils.DistanceUtils;
import org.apache.commons.text.RandomStringGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.apache.commons.text.CharacterPredicates.DIGITS;
import static org.apache.commons.text.CharacterPredicates.LETTERS;

@Service
public class UserService {
    private UserMapper userMapper;
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private ApplicationEventPublisher applicationEventPublisher;
    private UploadService uploadService;


    @Autowired
    public UserService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       ApplicationEventPublisher applicationEventPublisher,
                       UploadService uploadService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = UserMapper.INSTANCE;
        this.applicationEventPublisher = applicationEventPublisher;
        this.uploadService = uploadService;
    }

    @Transactional
    public void saveUser(UserRegisterDto userRegisterDto) {
        addPasswordToUser(userRegisterDto);
        saveUserAndSendEmail(userRegisterDto);
    }

    @Transactional
    public void saveVolunteer(UserRegisterDto userRegisterDto) {
        saveUserAndSendEmail(userRegisterDto);
    }

    public boolean isEmailAlreadyUsed(String email) {
        return userRepository.countByEmail(email) > 0;
    }

    public void changePassword(String username, String newPassword) {
        User user = getUserOrException(username);
        user.setPassword(passwordEncoder.encode(newPassword));

        userRepository.save(user);
    }

    public void saveLocationOnUser(UUID userId, LocationDto locationDto) {
        Location location = LocationMapper.INSTANCE.locationDtoToLocation(locationDto);
        User user = findUserById(userId.toString());
        user.setLocation(location);

        userRepository.save(user);
    }

    private void saveUserAndSendEmail(UserRegisterDto userRegisterDto) {
        User user = userMapper.userRegisterDtoToUser(userRegisterDto, passwordEncoder);
        user.setLocation(LocationMapper.INSTANCE.userRegisterDtoToLocation(userRegisterDto));

        if (userRegisterDto.getUploadedFile() != null) {
            String url = uploadService.uploadFile(userRegisterDto.getUploadedFile());
            user.setIdentityPhotoUrl(url);
        }

        User savedUser = userRepository.save(user);
        UserDto createdUserDto = userMapper.userToUserDto(savedUser);

        applicationEventPublisher.publishEvent(new UserCreatedEvent(this, createdUserDto, userRegisterDto.getOriginalPassword()));
    }

    public UserDto getUserDto(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("Username not found"));
        return userMapper.userToUserDto(user);
    }

    public User findUserById(String id) {
        return userRepository.findById(UUID.fromString(id)).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    public List<UserDto> findHelpedUsersInRange(UserFindDto userFindDto, DistancePreference distancePreference, int offset) {
        List<UserDto> userDtos = userMapper.usersToUserDtos(userRepository.findHelpedUsersInRange(
                userFindDto.getLocationId(), distancePreference.getFindDistanceInKM(), userFindDto.getId(), offset));

        return calculateDistanceForUserDtos(userFindDto, userDtos);
    }

    public List<UserDto> findUsersNeedHelpInRange(UserFindDto userFindDto, DistancePreference distancePreference, int offset) {
        List<UserDto> userDtos = userMapper.usersToUserDtos(userRepository.findUsersInRange(
                userFindDto.getLocationId(), distancePreference.getFindDistanceInKM(), userFindDto.getId(), offset));

        return calculateDistanceForUserDtos(userFindDto, userDtos);
    }

    public Integer countUsersInRange(UUID userId, UUID locationId, DistancePreference distancePreference) {
        return userRepository.countUsersInRange(userId, locationId, distancePreference.getFindDistanceInKM());
    }

    public void addUserToHelpedUsers(String loggedUserEmail, String userToBeHelpedId) {
        User loggedUser = getUserOrException(loggedUserEmail);
        User userToBeHelped = findUserById(userToBeHelpedId);
        loggedUser.getUsers().add(userToBeHelped);
        userRepository.save(loggedUser);
    }

    public List<UUID> getHelpedUsers(UUID loggedUserId) {
        return userRepository.getHelpedUsers(loggedUserId);
    }

    public void removeUserFromHelpedUsers(String loggedUserEmail, String userToBeRemovedId) {
        User loggedUser = getUserOrException(loggedUserEmail);
        User userToBeHelped = findUserById(userToBeRemovedId);
        loggedUser.getUsers().remove(userToBeHelped);
        userRepository.save(loggedUser);
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

    private LocationDto getPointDtoFromUser(String loggedUserEmail) {
        return userRepository.findByEmail(loggedUserEmail)
                .map(User::getLocation)
                .map(LocationMapper.INSTANCE::locationToLocationDto)
                .orElse(null);
    }

    private User getUserOrException(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("Username not found"));
    }

    private void addPasswordToUser(UserRegisterDto userRegisterDto) {
        String password = generatePassword();
        userRegisterDto.setPassword(password);
        userRegisterDto.setOriginalPassword(password);
    }

    private String generatePassword() {
        RandomStringGenerator generator = new RandomStringGenerator.Builder()
                .withinRange('0', 'z')
                .filteredBy(LETTERS, DIGITS)
                .build();
        return generator.generate(6, 10);
    }

}

