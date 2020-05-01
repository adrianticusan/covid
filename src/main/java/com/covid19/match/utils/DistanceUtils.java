package com.covid19.match.utils;

import com.covid19.match.dtos.LocationDto;
import com.covid19.match.dtos.PointDto;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.gavaghan.geodesy.Ellipsoid;
import org.gavaghan.geodesy.GeodeticCalculator;
import org.gavaghan.geodesy.GlobalPosition;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class DistanceUtils {

    public static double getDistanceBetweenPoints(LocationDto fromLocation, LocationDto toLocationDto) {
        GeodeticCalculator geoCalc = new GeodeticCalculator();
        Ellipsoid reference = Ellipsoid.WGS84;

        GlobalPosition loggedUserPosition = new GlobalPosition(fromLocation.getLatitude(), fromLocation.getLongitude(), 0.0);
        GlobalPosition userPosition = new GlobalPosition(toLocationDto.getLatitude(), toLocationDto.getLongitude(), 0.0);

        return geoCalc.calculateGeodeticCurve(reference, userPosition, loggedUserPosition).getEllipsoidalDistance() / 1000 * 0.00062137;
    }

    public static double fromMilesToKM(double miles) {
        return miles * 1.62137;
    }
}
