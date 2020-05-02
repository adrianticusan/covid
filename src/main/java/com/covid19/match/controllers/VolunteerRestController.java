package com.covid19.match.controllers;

import com.covid19.match.dtos.UserDto;
import com.covid19.match.dtos.UserFindDto;
import com.covid19.match.services.UserService;
import com.covid19.match.services.VolunteerService;
import com.covid19.match.session.DistancePreference;
import com.covid19.match.utils.UserHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping(value = "/volunteer/rest/")
public class VolunteerRestController {
    private VolunteerService volunteerService;
    private HttpSession httpSession;

    @Autowired
    public VolunteerRestController(UserService userService,
                               HttpSession httpSession) {
        this.volunteerService = volunteerService;
        this.httpSession = httpSession;
    }

    @RequestMapping(method = RequestMethod.GET, value = "findNextOrdered/need-help")
    public List<UserDto> findNextUsersInRangeNeedHelp(ModelAndView modelAndView, Integer offset) {
        UserFindDto userFindDto = UserHelper.getLoggedUserDto(SecurityContextHolder.getContext());
        DistancePreference distancePreference = (DistancePreference) httpSession.getAttribute("distancePreference");

        return volunteerService.findUsersNeedHelpInRange(userFindDto, distancePreference, offset);
    }
}
