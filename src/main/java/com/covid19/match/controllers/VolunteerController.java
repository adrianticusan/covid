package com.covid19.match.controllers;

import com.covid19.match.dtos.UserDto;
import com.covid19.match.entities.User;
import com.covid19.match.services.UserService;
import com.covid19.match.utils.UserHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.UUID;

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
    public ModelAndView findUsersInRange(ModelAndView modelAndView) {
        UserDto loggedUser = userService.getUserDto(UserHelper.getLoggedUserEmail(SecurityContextHolder.getContext()));
        List<UserDto> users = userService.findSortedUsersInRange(loggedUser, 0);
        modelAndView.addObject("users", users);
        modelAndView.addObject("helpedUsers", userService.getHelpedUsers(loggedUser));
        modelAndView.addObject("numberOfUsers", userService.countUsersInRange(loggedUser.getPositionDto().getLongitude(),
                loggedUser.getPositionDto().getLatitude()));
        modelAndView.setViewName("volunteer-page");
        return modelAndView;
    }

    @RequestMapping(method = RequestMethod.GET, value = "findNextOrdered")
    public ModelAndView findNextUsersInRange(ModelAndView modelAndView, Integer offset) {
        UserDto loggedUser = userService.getUserDto(UserHelper.getLoggedUserEmail(SecurityContextHolder.getContext()));
        List<UserDto> users = userService.findSortedUsersInRange(loggedUser, offset);
        modelAndView.addObject("helpedUsers", userService.getHelpedUsers(loggedUser));
        modelAndView.addObject("users", users);
        modelAndView.setViewName("users-table");
        return modelAndView;
    }

    @RequestMapping(method = RequestMethod.POST, value = "addUserToHelpedUsers")
    public ResponseEntity<String> addUserToHelpedUsers(String userToBeHelpedId) {
        userService.addUserToHelpedUsers(UserHelper.getLoggedUserEmail(SecurityContextHolder.getContext()),
                userToBeHelpedId);

        return ResponseEntity.ok().build();
    }

}
