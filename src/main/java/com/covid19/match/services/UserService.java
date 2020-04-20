package com.covid19.match.services;

import com.covid19.match.dtos.UserDto;
import com.covid19.match.dtos.UserRegisterDto;
import com.covid19.match.entities.User;
import com.covid19.match.events.UserCreatedEvent;
import com.covid19.match.mappers.UserMapper;
import com.covid19.match.repositories.UserRepository;
import org.apache.commons.text.RandomStringGenerator;
import com.covid19.match.utils.DistanceUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;

import static org.apache.commons.text.CharacterPredicates.DIGITS;
import static org.apache.commons.text.CharacterPredicates.LETTERS;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserService {
    private UserMapper userMapper;
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private Double userRangeInMeters;
    private ApplicationEventPublisher applicationEventPublisher;


    @Autowired
    public UserService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       ApplicationEventPublisher applicationEventPublisher,
                       @Value("${user.range.in.meters}") Double userRangeInMeters) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = UserMapper.INSTANCE;
        this.applicationEventPublisher = applicationEventPublisher;
        this.userRangeInMeters = userRangeInMeters;
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

    public List<User> findUsersInRange(double longitude, double latitude) {
        return userRepository.findUsersInRange(longitude, latitude, userRangeInMeters);
    }

    public boolean isEmailAlreadyUsed(String email) {
        return userRepository.countByEmail(email) > 0;
    }

    private void saveUserAndSendEmail(UserRegisterDto userRegisterDto) {
        User user = userRepository.save(userMapper.userRegisterDtoToUser(userRegisterDto, passwordEncoder));
        UserDto createdUserDto = userMapper.userToUserDto(user);
        applicationEventPublisher.publishEvent(new UserCreatedEvent(this, createdUserDto, userRegisterDto.getOriginalPassword()));
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

    public UserDto getUserDto(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("Username not found"));
        return userMapper.userToUserDto(user);
    }

    public User getUser(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("Username not found"));
    }

    public User findUserById(String id) {
        return userRepository.findById(UUID.fromString(id)).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    public List<UserDto> findSortedUsersInRange(UserDto loggedUser, int offset) {
        List<UserDto> userDtos = userMapper.usersToUserDtos(userRepository.findSortedUsersInRange(
                loggedUser.getPositionDto().getLongitude(), loggedUser.getPositionDto().getLatitude(),
                userRangeInMeters, offset));
        if (CollectionUtils.isEmpty(userDtos)) {
            return Collections.emptyList();
        }
        userDtos.forEach(user -> user.getPositionDto().setDistanceInKm(
                DistanceUtils.getDistanceBetweenPoints(user.getPositionDto(), loggedUser.getPositionDto())));
        return userDtos;
    }

    public Integer countUsersInRange(double longitude, double latitude) {
        return userRepository.countUsersInRange(longitude, latitude, userRangeInMeters);
    }

    public void addUserToHelpedUsers(String loggedUserEmail, String userToBeHelpedId) {
        User loggedUser = getUser(loggedUserEmail);
        User userToBeHelped = findUserById(userToBeHelpedId);
        loggedUser.getUsers().add(userToBeHelped);
        userRepository.save(loggedUser);
    }

    public List<UUID> getHelpedUsers(UserDto loggedUser) {
        return loggedUser.getUsers().stream().map(UserDto::getId).collect(Collectors.toList());
    }

}

