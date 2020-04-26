package com.covid19.match.controllers;

import com.covid19.match.dtos.UserDto;
import com.covid19.match.dtos.UserFindDto;
import com.covid19.match.entities.User;
import com.covid19.match.services.UserService;
import com.covid19.match.utils.UserHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(value = "/volunteer/")
public class VolunteerController {
    private UserService userService;

    @Autowired
    public VolunteerController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(method = RequestMethod.GET, value = {"/"})
    public ModelAndView getHelped(ModelAndView modelAndView) {
        modelAndView = getModel(modelAndView);

        UserFindDto userFindDto = UserHelper.getLoggedUserEmail(SecurityContextHolder.getContext());
        List<UserDto> users = userService.findHelpedUsersInRange(userFindDto, 0);

        modelAndView.addObject("users", users);
        modelAndView.setViewName("vounteer-hepled-people");
        return modelAndView;
    }

    @RequestMapping(method = RequestMethod.GET, value = {"/need-help"})
    public ModelAndView getNeedHelp(ModelAndView modelAndView) {
        modelAndView = getModel(modelAndView);

        UserFindDto userFindDto = UserHelper.getLoggedUserEmail(SecurityContextHolder.getContext());
        List<UserDto> users = userService.findUsersNeedHelpInRange(userFindDto, 0);
        modelAndView.addObject("users", users);
        modelAndView.setViewName("volunteer-page");

        return modelAndView;
    }

    @RequestMapping(method = RequestMethod.GET, value = "findNextOrdered")
    public ModelAndView findNextUsersInRange(ModelAndView modelAndView, Integer offset) {
        modelAndView = getModel(modelAndView);
        UserFindDto userFindDto = UserHelper.getLoggedUserEmail(SecurityContextHolder.getContext());
        List<UserDto> users = userService.findHelpedUsersInRange(userFindDto, offset);

        modelAndView.addObject("users", users);
        modelAndView.setViewName("users-table");
        return modelAndView;
    }

    @RequestMapping(method = RequestMethod.GET, value = "findNextOrdered/need-help")
    public ModelAndView findNextUsersInRangeNeedHelp(ModelAndView modelAndView, Integer offset) {
        modelAndView = getModel(modelAndView);
        UserFindDto userFindDto = UserHelper.getLoggedUserEmail(SecurityContextHolder.getContext());
        List<UserDto> users = userService.findUsersNeedHelpInRange(userFindDto, offset);

        modelAndView.addObject("users", users);
        modelAndView.setViewName("users-table");
        return modelAndView;
    }

    @RequestMapping(method = RequestMethod.POST, value = "help-user")
    public ResponseEntity<String> addUserToHelpedUsers(String userToBeHelpedId) {
        userService.addUserToHelpedUsers(UserHelper.getLoggedUserEmail(SecurityContextHolder.getContext()).getEmail(),
                userToBeHelpedId);

        return ResponseEntity.ok().build();
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "stop-helping-user")
    public ResponseEntity<String> removeUserFromHelpedUsers(String userToBeRemovedId) {
        userService.removeUserFromHelpedUsers(UserHelper.getLoggedUserEmail(SecurityContextHolder.getContext()).getEmail(),
        userToBeRemovedId);

        return ResponseEntity.ok().build();
    }

    private ModelAndView getModel(ModelAndView modelAndView) {
        UserFindDto userFindDto = UserHelper.getLoggedUserEmail(SecurityContextHolder.getContext());
        modelAndView.addObject("helpedUsers", userService.getHelpedUsers(userFindDto.getId()));
        modelAndView.addObject("numberOfUsers", userService.countUsersInRange(userFindDto.getEmail(),
                userFindDto.getId()));

        return modelAndView;
    }

}
