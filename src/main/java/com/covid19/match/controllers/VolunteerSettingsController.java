package com.covid19.match.controllers;

import com.covid19.match.dtos.ChangePasswordDto;
import com.covid19.match.dtos.LocationDto;
import com.covid19.match.dtos.UserFindDto;
import com.covid19.match.services.UserService;
import com.covid19.match.session.DistancePreference;
import com.covid19.match.utils.UserHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
@RequestMapping(value = "/volunteer/preferences/")
public class VolunteerSettingsController {
    private UserService userService;
    private HttpSession httpSession;

    @Autowired
    public VolunteerSettingsController(UserService userService,
                               HttpSession httpSession) {
        this.userService = userService;
        this.httpSession = httpSession;
    }


    @RequestMapping(method = RequestMethod.GET, value = "change-location")
    public ModelAndView getChangeLocation(ModelAndView modelAndView) {
        String currentUserEmail = UserHelper.getLoggedUserDto(SecurityContextHolder.getContext()).getEmail();
        DistancePreference distancePreference = (DistancePreference) httpSession.getAttribute("distancePreference");

        modelAndView = getModel(modelAndView);
        modelAndView.addObject("currentUser", userService.getUserDto(currentUserEmail));
        modelAndView.addObject("distancePreference", distancePreference);
        modelAndView.addObject("locationDto", new LocationDto());
        modelAndView.setViewName("v-settings-change-location");

        return modelAndView;
    }

    @RequestMapping(method = RequestMethod.POST, value = "change-location")
    public ModelAndView getChangeLocation(ModelAndView modelAndView, @Valid LocationDto locationDto,
                                          BindingResult bindingResult) {
        UserFindDto currentUser = UserHelper.getLoggedUserDto(SecurityContextHolder.getContext());

        if (!bindingResult.hasErrors()) {
            userService.saveLocationOnUser(currentUser.getId(), locationDto);
            return new ModelAndView("redirect:/volunteer/change-location");
        }

        DistancePreference distancePreference = (DistancePreference) httpSession.getAttribute(DistancePreference.NAME);

        modelAndView = getModel(modelAndView);
        modelAndView.addObject("currentUser", userService.getUserDto(currentUser.getEmail()));
        modelAndView.addObject("distancePreference", distancePreference);
        modelAndView.addObject("locationDto", locationDto);
        modelAndView.setViewName("v-settings-change-location");

        return modelAndView;
    }

    @RequestMapping(method = RequestMethod.GET, value = "change-password")
    public ModelAndView getChangePassword(ModelAndView modelAndView) {
        modelAndView = getModel(modelAndView);
        modelAndView.addObject("changePasswordDto", new ChangePasswordDto());
        modelAndView.setViewName("v-settings-change-pass");

        return modelAndView;
    }

    @RequestMapping(method = RequestMethod.POST, value = "change-password")
    public ModelAndView postChangePassword(@ModelAttribute(name = "changePasswordDto") @Valid ChangePasswordDto changePasswordDto,
                                           BindingResult bindingResult, ModelAndView modelAndView) {
        modelAndView = getModel(modelAndView);

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
        UserFindDto loggedUser = UserHelper.getLoggedUserDto(SecurityContextHolder.getContext());
        DistancePreference distancePreference = (DistancePreference) httpSession.getAttribute(DistancePreference.NAME);
        modelAndView.addObject("numberOfUsers", userService.countUsersInRange(loggedUser.getId(), loggedUser.getLocationId(), distancePreference));

        return modelAndView;
    }
}
