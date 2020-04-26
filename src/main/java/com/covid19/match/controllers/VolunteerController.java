package com.covid19.match.controllers;

import com.covid19.match.dtos.ChangePasswordDto;
import com.covid19.match.dtos.UserDto;
import com.covid19.match.dtos.UserFindDto;
import com.covid19.match.services.UserService;
import com.covid19.match.utils.UserHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;


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

        UserFindDto userFindDto = UserHelper.getLoggedUserDto(SecurityContextHolder.getContext());
        List<UserDto> users = userService.findHelpedUsersInRange(userFindDto, 0);

        modelAndView.addObject("users", users);
        modelAndView.setViewName("vounteer-hepled-people");
        return modelAndView;
    }

    @RequestMapping(method = RequestMethod.GET, value = {"/need-help"})
    public ModelAndView getNeedHelp(ModelAndView modelAndView) {
        modelAndView = getModel(modelAndView);

        UserFindDto userFindDto = UserHelper.getLoggedUserDto(SecurityContextHolder.getContext());
        List<UserDto> users = userService.findUsersNeedHelpInRange(userFindDto, 0);
        modelAndView.addObject("users", users);
        modelAndView.setViewName("volunteer-page");

        return modelAndView;
    }

    @RequestMapping(method = RequestMethod.GET, value = "findNextOrdered")
    public ModelAndView findNextUsersInRange(ModelAndView modelAndView, Integer offset) {
        modelAndView = getModel(modelAndView);
        UserFindDto userFindDto = UserHelper.getLoggedUserDto(SecurityContextHolder.getContext());
        List<UserDto> users = userService.findHelpedUsersInRange(userFindDto, offset);

        modelAndView.addObject("users", users);
        modelAndView.setViewName("users-table");
        return modelAndView;
    }

    @RequestMapping(method = RequestMethod.GET, value = "findNextOrdered/need-help")
    public ModelAndView findNextUsersInRangeNeedHelp(ModelAndView modelAndView, Integer offset) {
        modelAndView = getModel(modelAndView);
        UserFindDto userFindDto = UserHelper.getLoggedUserDto(SecurityContextHolder.getContext());
        List<UserDto> users = userService.findUsersNeedHelpInRange(userFindDto, offset);

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

    @RequestMapping(method = RequestMethod.GET, value = "change-location")
    public String getChangeLocation() {
        return "v-settings-change-location";
    }


    @RequestMapping(method = RequestMethod.GET, value = "change-password")
    public ModelAndView getChangePassword(ModelAndView modelAndView) {
        modelAndView.addObject("changePasswordDto", new ChangePasswordDto());
        modelAndView.setViewName("v-settings-change-pass");

        return modelAndView;
    }

    @RequestMapping(method = RequestMethod.POST, value = "change-password")
    public ModelAndView postChangePassword(@ModelAttribute(name = "changePasswordDto") @Valid ChangePasswordDto changePasswordDto,
                                           BindingResult bindingResult, ModelAndView modelAndView) {
        if (bindingResult.hasErrors()) {
            modelAndView.addObject("changePasswordDto", changePasswordDto);
            modelAndView.setViewName("v-settings-change-pass");
            return modelAndView;
        }

        String loggedUserEmail = UserHelper.getLoggedUserEmail(SecurityContextHolder.getContext());
        userService.changePassword(loggedUserEmail, changePasswordDto.getNewPassword());

        modelAndView.addObject("changePasswordDto", new ChangePasswordDto());
        modelAndView.setViewName("v-settings-change-pass");

        return modelAndView;
    }

    private ModelAndView getModel(ModelAndView modelAndView) {
        UserFindDto userFindDto = UserHelper.getLoggedUserDto(SecurityContextHolder.getContext());
        modelAndView.addObject("helpedUsers", userService.getHelpedUsers(userFindDto.getId()));
        modelAndView.addObject("numberOfUsers", userService.countUsersInRange(userFindDto.getEmail(),
                userFindDto.getId()));
        return modelAndView;
    }

}
