package com.covid19.match.services;

import com.covid19.match.amazon.services.UploadService;
import com.covid19.match.dtos.PointDto;
import com.covid19.match.dtos.UserDto;
import com.covid19.match.dtos.UserFindDto;
import com.covid19.match.dtos.UserRegisterDto;
import com.covid19.match.entities.User;
import com.covid19.match.events.UserCreatedEvent;
import com.covid19.match.mappers.UserMapper;
import com.covid19.match.repositories.UserRepository;
import com.covid19.match.utils.DistanceUtils;
import org.apache.commons.text.RandomStringGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.transaction.Transactional;
import java.util.*;

import static org.apache.commons.text.CharacterPredicates.DIGITS;
import static org.apache.commons.text.CharacterPredicates.LETTERS;

@Service
public class UserService {
    private UserMapper userMapper;
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private Double userRangeInMeters;
    private ApplicationEventPublisher applicationEventPublisher;
    private UploadService uploadService;


    @Autowired
    public UserService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       ApplicationEventPublisher applicationEventPublisher,
                       @Value("${user.range.in.meters}") Double userRangeInMeters,
                       UploadService uploadService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = UserMapper.INSTANCE;
        this.applicationEventPublisher = applicationEventPublisher;
        this.userRangeInMeters = userRangeInMeters;
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

    private void saveUserAndSendEmail(UserRegisterDto userRegisterDto) {
        User user = userMapper.userRegisterDtoToUser(userRegisterDto, passwordEncoder);
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

    public List<UserDto> findHelpedUsersInRange(UserFindDto userFindDto, int offset) {
        List<UserDto> userDtos = userMapper.usersToUserDtos(userRepository.findHelpedUsersInRange(
                userFindDto.getEmail(), userRangeInMeters, userFindDto.getId(), offset));

        return calculateDistanceForUserDtos(userFindDto, userDtos);
    }

    public List<UserDto> findUsersNeedHelpInRange(UserFindDto userFindDto, int offset) {
        List<UserDto> userDtos =  userMapper.usersToUserDtos(userRepository.findUsersInRange(
                userFindDto.getEmail(), userRangeInMeters, userFindDto.getId(), offset));

        return calculateDistanceForUserDtos(userFindDto, userDtos);
    }

    public Integer countUsersInRange(String loggedUserEmail, UUID loggedUserId) {
        return userRepository.countUsersInRange(loggedUserEmail, userRangeInMeters, loggedUserId);
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

    private PointDto getPointDtoFromUser(String loggedUserEmail) {
        return userRepository.findByEmail(loggedUserEmail)
                .map(User::getPosition)
                .map(p -> userMapper.pointToPointDto(p))
                .orElse(null);
    }

    private List<UserDto> calculateDistanceForUserDtos(UserFindDto userFindDto, List<UserDto> userDtos) {
        if (CollectionUtils.isEmpty(userDtos)) {
            return Collections.emptyList();
        }

        PointDto loggedUserPosition = getPointDtoFromUser(userFindDto.getEmail());
        userDtos.forEach(user -> user.getPositionDto().setDistanceInKm(
                DistanceUtils.getDistanceBetweenPoints(user.getPositionDto(), loggedUserPosition)));

        return userDtos;
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

