package com.covid19.match.services;

import com.covid19.match.dtos.UserRegisterDto;
import com.covid19.match.entities.Role;
import com.covid19.match.entities.User;
import com.covid19.match.mappers.UserMapper;
import com.covid19.match.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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


    @Autowired
    public UserService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       @Value("${user.range.in.meters}") Double userRangeInMeters) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = UserMapper.INSTANCE;
        this.userRangeInMeters = userRangeInMeters;
    }

    @Transactional
    public void saveUser(UserRegisterDto userRegisterDto) {
        userRepository.save(userMapper.userRegisterDtoToUser(userRegisterDto, passwordEncoder));
    }

    public List<User> findUsersInRange(double longitude, double latitude) {
       return userRepository.findUsersInRange(longitude, latitude, userRangeInMeters);
    }
}
