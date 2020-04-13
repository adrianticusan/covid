package com.covid19.match.services;

import com.covid19.match.dtos.UserDto;
import com.covid19.match.dtos.UserRegisterDto;
import com.covid19.match.entities.User;
import com.covid19.match.events.UserCreatedEvent;
import com.covid19.match.mappers.UserMapper;
import com.covid19.match.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

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
        User user = userRepository.save(userMapper.userRegisterDtoToUser(userRegisterDto, passwordEncoder));
        UserDto createdUserDto = userMapper.userToUserDto(user);
        applicationEventPublisher.publishEvent(new UserCreatedEvent(this, createdUserDto, userRegisterDto.getOriginalPassword()));
    }

    public List<User> findUsersInRange(double longitude, double latitude) {
       return userRepository.findUsersInRange(longitude, latitude, userRangeInMeters);
    }
}
