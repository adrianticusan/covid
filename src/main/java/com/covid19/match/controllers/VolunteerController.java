package com.covid19.match.controllers;

import com.covid19.match.dtos.UserDto;
import com.covid19.match.dtos.UserFindDto;
import com.covid19.match.services.UserService;
import com.covid19.match.session.DistancePreference;
import com.covid19.match.utils.UserHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.List;


@Controller
@RequestMapping(value = "/volunteer/")
public class VolunteerController {
    private UserService userService;
    private HttpSession httpSession;

    @Autowired
    public VolunteerController(UserService userService,
                               HttpSession httpSession) {
        this.userService = userService;
        this.httpSession = httpSession;
    }

    @RequestMapping(method = RequestMethod.GET, value = {"/"})
    public ModelAndView getHelped(ModelAndView modelAndView) {
        modelAndView = getModel(modelAndView);

        UserFindDto loggedUserDto = UserHelper.getLoggedUserDto(SecurityContextHolder.getContext());
        DistancePreference distancePreference = (DistancePreference) httpSession.getAttribute("distancePreference");
        List<UserDto> users = userService.findHelpedUsersInRange(loggedUserDto, distancePreference, 0);

        modelAndView.addObject("users", users);
        modelAndView.setViewName("vounteer-hepled-people");
        return modelAndView;
    }

    @RequestMapping(method = RequestMethod.GET, value = {"/need-help"})
    public ModelAndView getNeedHelp(ModelAndView modelAndView) {
        modelAndView = getModel(modelAndView);

        UserFindDto userFindDto = UserHelper.getLoggedUserDto(SecurityContextHolder.getContext());
        DistancePreference distancePreference = (DistancePreference) httpSession.getAttribute("distancePreference");
        List<UserDto> users = userService.findUsersNeedHelpInRange(userFindDto, distancePreference, 0);
        modelAndView.addObject("users", users);
        modelAndView.setViewName("volunteer-page");

        return modelAndView;
    }

    @RequestMapping(method = RequestMethod.GET, value = "findNextOrdered")
    public ModelAndView findNextUsersInRange(ModelAndView modelAndView, Integer offset) {
        modelAndView = getModel(modelAndView);
        UserFindDto userFindDto = UserHelper.getLoggedUserDto(SecurityContextHolder.getContext());
        DistancePreference distancePreference = (DistancePreference) httpSession.getAttribute("distancePreference");
        List<UserDto> users = userService.findHelpedUsersInRange(userFindDto, distancePreference, offset);

        modelAndView.addObject("users", users);
        modelAndView.setViewName("users-table");
        return modelAndView;
    }

    @RequestMapping(method = RequestMethod.GET, value = "findNextOrdered/need-help")
    public ModelAndView findNextUsersInRangeNeedHelp(ModelAndView modelAndView, Integer offset) {
        UserFindDto userFindDto = UserHelper.getLoggedUserDto(SecurityContextHolder.getContext());
        DistancePreference distancePreference = (DistancePreference) httpSession.getAttribute("distancePreference");
        List<UserDto> users = userService.findUsersNeedHelpInRange(userFindDto, distancePreference, offset);

        modelAndView = getModel(modelAndView);
        modelAndView.addObject("users", users);
        modelAndView.setViewName("users-table");
        return modelAndView;
    }

    @RequestMapping(method = RequestMethod.POST, value = "help-user")
    public ResponseEntity<String> addUserToHelpedUsers(String userToBeHelpedId) {
        userService.addUserToHelpedUsers(UserHelper.getLoggedUserDto(SecurityContextHolder.getContext()).getEmail(),
                userToBeHelpedId);

        return ResponseEntity.ok().build();
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "stop-helping-user")
    public ResponseEntity<String> removeUserFromHelpedUsers(String userToBeRemovedId) {
        userService.removeUserFromHelpedUsers(UserHelper.getLoggedUserDto(SecurityContextHolder.getContext()).getEmail(),
                userToBeRemovedId);

        return ResponseEntity.ok().build();
    }

    private ModelAndView getModel(ModelAndView modelAndView) {
        UserFindDto loggedUser = UserHelper.getLoggedUserDto(SecurityContextHolder.getContext());
        DistancePreference distancePreference = (DistancePreference) httpSession.getAttribute(DistancePreference.NAME);
        modelAndView.addObject("numberOfUsers", userService.countUsersInRange(loggedUser.getId(), loggedUser.getLocationId(), distancePreference));

        return modelAndView;
    }

}
