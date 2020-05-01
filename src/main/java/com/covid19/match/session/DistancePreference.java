package com.covid19.match.session;

import com.covid19.match.enums.DistanceUnit;
import com.covid19.match.utils.DistanceUtils;
import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component(value = DistancePreference.NAME)
@Scope(value = "session")
@Data
public class DistancePreference {
    private int findDistance;
    private DistanceUnit distanceUnit;
    public final static String NAME = "distancePreference";

    public double getFindDistanceInKM() {
        if (DistanceUnit.KM.equals(distanceUnit)) {
            return findDistance;
        }

        return DistanceUtils.fromMilesToKM(findDistance);
    }
}
