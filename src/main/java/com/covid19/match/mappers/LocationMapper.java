package com.covid19.match.mappers;

import com.covid19.match.dtos.LocationDto;
import com.covid19.match.dtos.PointDto;
import com.covid19.match.dtos.UserRegisterDto;
import com.covid19.match.entities.Location;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

@Mapper
public interface LocationMapper {
    LocationMapper INSTANCE = Mappers.getMapper(LocationMapper.class);

    Location locationDtoToLocation(LocationDto locationDto);

    LocationDto locationToLocationDto(Location location);

    Location userRegisterDtoToLocation(UserRegisterDto userRegisterDto);

    @AfterMapping
    default void mapLocation(@MappingTarget Location location, UserRegisterDto userRegisterDto) {
        GeometryFactory gf = new GeometryFactory();
        Point point = gf.createPoint(new Coordinate(userRegisterDto.getLongitude(), userRegisterDto.getLatitude()));
        location.setCurrentPosition(point);
    }

    @AfterMapping
    default void mapLocation(@MappingTarget LocationDto locationDto, Location location) {
      locationDto.setLatitude(location.getCurrentPosition().getY());
      locationDto.setLongitude(location.getCurrentPosition().getX());
    }

    @Named("pointToPointDto")
    default PointDto pointToPointDto(Point point) {
        PointDto pointDto = new PointDto();
        pointDto.setLatitude(point.getCoordinate().y);
        pointDto.setLongitude(point.getCoordinate().x);
        return pointDto;
    }

    @AfterMapping
    default void mapLocation(@MappingTarget Location location, LocationDto locationDto) {
        GeometryFactory gf = new GeometryFactory();
        Point point = gf.createPoint(new Coordinate(locationDto.getLongitude(), locationDto.getLatitude()));
        location.setCurrentPosition(point);
    }

}
