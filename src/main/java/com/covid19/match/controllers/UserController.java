package com.covid19.match.controllers;

import com.covid19.match.dtos.UserRegisterDto;
import com.covid19.match.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
@RequestMapping(value = "/user/")
public class UserController {
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(method = RequestMethod.GET, value = "login")
    public String getLogin() {
        return "login";
    }

    @RequestMapping(method = RequestMethod.GET, value = "register")
    public ModelAndView getRegister(ModelAndView modelAndView) {
        modelAndView.addObject("userRegisterDto", new UserRegisterDto());
        modelAndView.setViewName("register");
        return modelAndView;
    }

    @RequestMapping(method = RequestMethod.POST, value = "register")
    public String postRegister(@Valid @ModelAttribute(name = "userRegisterDto") UserRegisterDto userRegisterDto, BindingResult result) {
        if (!result.hasErrors()) {
            userService.saveUser(userRegisterDto);
            return "register";
        }

        return "register";

    }
}
