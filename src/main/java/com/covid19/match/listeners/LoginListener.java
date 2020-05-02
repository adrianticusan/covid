package com.covid19.match.listeners;

import com.covid19.match.dtos.UserDto;
import com.covid19.match.enums.DistanceUnit;
import com.covid19.match.services.UserService;
import com.covid19.match.session.DistancePreference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AbstractAuthenticationEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.authentication.event.InteractiveAuthenticationSuccessEvent;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;


@Component
public class LoginListener {
    private UserService userService;
    private int defaultUserRangeInMeters;
    private DistanceUnit defaultUnit;
    HttpSession httpSession;

    @Autowired
    public LoginListener(@Value("${user.range.in.meters}") int defaultUserRangeInMeters,
                         DistanceUnit defaultUnit,
                         UserService userService,
                         HttpSession httpSession) {
        this.defaultUserRangeInMeters = defaultUserRangeInMeters;
        this.defaultUnit = defaultUnit;
        this.userService = userService;
        this.httpSession = httpSession;
    }

    @EventListener({AuthenticationSuccessEvent.class, InteractiveAuthenticationSuccessEvent.class})
    public void onApplicationEvent(AbstractAuthenticationEvent event) {
        UserDetails userDetails = (UserDetails) event.getAuthentication().getPrincipal();
        UserDto userDto = userService.getUserDto(userDetails.getUsername());

        if (!userDto.isVolunteer()) {
            return;
        }

        setPreferences(userDto);
    }

    public void setPreferences(UserDto userDto) {
        DistancePreference distancePreference = new DistancePreference();
        distancePreference.setDistanceUnit(userDto.getDistanceUnit() != null ? userDto.getDistanceUnit() : defaultUnit);
        distancePreference.setFindDistance(userDto.getFindDistance() != null ? userDto.getFindDistance() : defaultUserRangeInMeters/1000);

        httpSession.setAttribute(DistancePreference.NAME, distancePreference);
    }
}
