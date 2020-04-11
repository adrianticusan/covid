package com.covid19.match.utils;

import com.covid19.match.dtos.PointDto;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.gavaghan.geodesy.Ellipsoid;
import org.gavaghan.geodesy.GeodeticCalculator;
import org.gavaghan.geodesy.GlobalPosition;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class DistanceUtils {

    public static double getUserDistance(PointDto pointDto, PointDto userPointDto) {
        GeodeticCalculator geoCalc = new GeodeticCalculator();
        Ellipsoid reference = Ellipsoid.WGS84;

        GlobalPosition loggedUserPosition = new GlobalPosition(pointDto.getLatitude(), pointDto.getLongitude(), 0.0);
        GlobalPosition userPosition = new GlobalPosition(userPointDto.getLatitude(), userPointDto.getLongitude(), 0.0);
        double distance = geoCalc.calculateGeodeticCurve(reference, userPosition, loggedUserPosition).getEllipsoidalDistance() / 1000;

        return distance;
    }
}
