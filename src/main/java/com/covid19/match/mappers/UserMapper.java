package com.covid19.match.mappers;

import com.covid19.match.dtos.UserDto;
import com.covid19.match.dtos.UserRegisterDto;
import com.covid19.match.entities.Role;
import com.covid19.match.entities.User;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

@Mapper
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    User userRegisterDtoToUser(UserRegisterDto userRegisterDto, @Context PasswordEncoder passwordEncoder);

    UserDto userToUserDto(User user);

    @BeforeMapping
    default void encodePassword(UserRegisterDto userRegisterDto, @Context PasswordEncoder passwordEncoder) {
        userRegisterDto.setOriginalPassword(userRegisterDto.getPassword());
        userRegisterDto.setPassword(passwordEncoder.encode(userRegisterDto.getPassword()));
    }

    @AfterMapping
    default void setPasswordEncoder(@MappingTarget User user, UserRegisterDto userRegisterDto) {
        GeometryFactory gf = new GeometryFactory();
        Point point = gf.createPoint(new Coordinate(userRegisterDto.getLongitude(), userRegisterDto.getLatitude()));
        user.setPosition(point);
        user.setRole(Role.USER);
        user.setVolunteer(Optional.ofNullable(userRegisterDto.getIsVolunteer()).orElse(false));
    }
}
