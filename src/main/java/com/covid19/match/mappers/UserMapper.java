package com.covid19.match.mappers;

import com.covid19.match.dtos.UserDisplayDto;
import com.covid19.match.dtos.UserDto;
import com.covid19.match.dtos.UserRegisterDto;
import com.covid19.match.entities.Role;
import com.covid19.match.entities.Settings;
import com.covid19.match.entities.User;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

@Mapper(uses = LocationMapper.class)
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    User userRegisterDtoToUser(UserRegisterDto userRegisterDto, @Context PasswordEncoder passwordEncoder);

    @Mapping(target = "users", ignore = true)
    @Mapping(target = "locationDto", source = "location")
    UserDto userToUserDto(User user);

    List<UserDto> usersToUserDtos(List<User> user);

    List<UserDisplayDto> userDtoToUserDisplayDto(List<UserDto> user);

    @BeforeMapping
    default void encodePassword(UserRegisterDto userRegisterDto, @Context PasswordEncoder passwordEncoder) {
        userRegisterDto.setOriginalPassword(userRegisterDto.getPassword());
        userRegisterDto.setPassword(passwordEncoder.encode(userRegisterDto.getPassword()));
    }

    @AfterMapping
    default void setSettings(@MappingTarget UserDto userDto, User user) {
        Integer distance = Optional.ofNullable(user.getSettings())
                .map(Settings::getDistance)
                .orElse(null);

        if (distance != null) {
            userDto.setFindDistance(distance);
            userDto.setDistanceUnit(user.getSettings().getDistanceUnit());
        }
    }

    @AfterMapping
    default void setPasswordEncoder(@MappingTarget User user, UserRegisterDto userRegisterDto) {
        user.setRole(Role.USER);
        user.setVolunteer(Optional.ofNullable(userRegisterDto.getIsVolunteer()).orElse(false));
    }
}
