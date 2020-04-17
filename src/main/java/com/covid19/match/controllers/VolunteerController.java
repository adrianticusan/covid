package com.covid19.match.controllers;

import com.covid19.match.dtos.UserDto;
import com.covid19.match.entities.User;
import com.covid19.match.services.UserService;
import com.covid19.match.utils.UserHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping(value = "/volunteer/")
public class VolunteerController {
    private UserService userService;

    @Autowired
    public VolunteerController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(method = RequestMethod.GET, value = "find")
    public String findUsersInRange(Double longitude, Double latitude) {
        List<User> users = userService.findUsersInRange(longitude, latitude);

        users.stream().peek(u -> System.out.println(u.getFirstName()));
        return "";
    }

    @RequestMapping(method = RequestMethod.GET, value = "findOrdered")
    public ModelAndView findUsersInRange(Double longitude, Double latitude, Double userRangeInMeters,
                                         ModelAndView modelAndView) {
        List<UserDto> users = userService.findSortedUsersInRange(longitude, latitude, userRangeInMeters,
                UserHelper.getLoggedUserEmail(SecurityContextHolder.getContext()), 0);
        modelAndView.addObject("users", users);
        modelAndView.addObject("numberOfUsers", userService.countUsersInRange(longitude,
                latitude, userRangeInMeters));
        modelAndView.setViewName("volunteer-page");
        return modelAndView;
    }

    @RequestMapping(method = RequestMethod.GET, value = "findNextOrdered")
    public ModelAndView findNextUsersInRange(Double longitude, Double latitude, Double userRangeInMeters,
                                             ModelAndView modelAndView, Integer offset) {
        List<UserDto> users = userService.findSortedUsersInRange(longitude, latitude, userRangeInMeters,
                UserHelper.getLoggedUserEmail(SecurityContextHolder.getContext()), offset);
        modelAndView.addObject("users", users);
        modelAndView.setViewName("users-table");
        return modelAndView;
    }

}
