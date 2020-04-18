package com.covid19.match.controllers;

import com.covid19.match.configs.security.SecurityService;
import com.covid19.match.dtos.UserRegisterDto;
import com.covid19.match.entities.User;
import com.covid19.match.google.api.GoogleRecaptchaApi;
import com.covid19.match.services.UserService;
import com.covid19.match.validation.groups.VolunteerValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.groups.Default;
import java.util.List;

@Controller
@RequestMapping(value = "/user/")
public class UserController {
    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(method = RequestMethod.GET, value = "login")
    public String getLogin() {
        return "login";
    }

    @RequestMapping(method = RequestMethod.POST, value = "register")
    public ModelAndView postRegisterUser(@ModelAttribute(name = "userRegisterDto") @Validated({Default.class}) UserRegisterDto userRegisterDto,
                             BindingResult result, ModelAndView modelAndView) {
        if (!result.hasErrors()) {
            userService.saveUser(userRegisterDto);
        }

        modelAndView.addObject("userRegisterDto", userRegisterDto);
        modelAndView.addObject("volunteerRegisterDto", new UserRegisterDto());
        modelAndView.setViewName("index");

        return modelAndView;
    }

    @RequestMapping(method = RequestMethod.POST, value = "register/volunteer")
    public ModelAndView postRegisterVolunteer(@ModelAttribute(name = "volunteerRegisterDto") @Validated({Default.class, VolunteerValidation.class})
                                                          UserRegisterDto userRegisterDto,
                                     BindingResult result, ModelAndView modelAndView) {
        if (!result.hasErrors()) {
            userService.saveUser(userRegisterDto);
            return new ModelAndView("redirect:/home/");
        }

        modelAndView.addObject("volunteerRegisterDto", userRegisterDto);
        modelAndView.addObject("userRegisterDto", new UserRegisterDto());
        modelAndView.setViewName("index");

        return modelAndView;
    }

    @RequestMapping(method = RequestMethod.GET, value = "find")
    public String findUsersInRange(Double longitude, Double latitude) {
        List<User> users = userService.findUsersInRange(longitude, latitude);

        users.stream().peek(u -> System.out.println(u.getFirstName()));
        return "";
    }
}
