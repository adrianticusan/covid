package com.covid19.match.controllers;

import com.covid19.match.dtos.UserDisplayDto;
import com.covid19.match.dtos.UserDto;
import com.covid19.match.dtos.UserFindDto;
import com.covid19.match.enums.DistanceUnit;
import com.covid19.match.services.UserService;
import com.covid19.match.services.VolunteerService;
import com.covid19.match.session.DistancePreference;
import com.covid19.match.utils.UserHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping(value = "/volunteer/rest/")
public class VolunteerRestController {
    private VolunteerService volunteerService;
    private HttpSession httpSession;
    private DistanceUnit defaultUnit;
    private static final int LIMIT = 1000;
    private static final int OFFSET = 0;

    @Autowired
    public VolunteerRestController(VolunteerService volunteerService,
                                   DistanceUnit defaultUnit,
                                   HttpSession httpSession) {
        this.volunteerService = volunteerService;
        this.defaultUnit = defaultUnit;
        this.httpSession = httpSession;
    }

    @GetMapping(value = "find-users-map")
    public List<UserDisplayDto> findNextUsersInRangeNeedHelp(double latitude, double longitude) {
        UserFindDto userFindDto = UserHelper.getLoggedUserDto(SecurityContextHolder.getContext());
        DistancePreference distancePreference = (DistancePreference) httpSession.getAttribute(DistancePreference.NAME);

        return volunteerService.findUsersNeedHelpInRange(userFindDto, distancePreference, OFFSET, LIMIT);
    }

    @PostMapping(value = "update-distance-settings")
    public ResponseEntity<Void> updateDistanceSettings(int distance) {
        UserFindDto userFindDto = UserHelper.getLoggedUserDto(SecurityContextHolder.getContext());
        DistancePreference distancePreference = DistancePreference.builder()
                .findDistance(distance)
                .distanceUnit(defaultUnit)
                .build();
        volunteerService.updateDistancePreferences(userFindDto, distancePreference);
        httpSession.setAttribute(DistancePreference.NAME, distancePreference);

        return ResponseEntity.ok().build();
    }
}
