package com.covid19.match.controllers;

import com.covid19.match.dtos.ContactDto;
import com.covid19.match.dtos.UserRegisterDto;
import com.covid19.match.services.UserService;
import com.covid19.match.validation.groups.VolunteerValidation;
import com.covid19.match.validators.AddressValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.groups.Default;

@Controller
@RequestMapping(value = "/user/")
public class UserController {
    private UserService userService;
    private MessageSource messageSource;
    private Validator validator;

    @Autowired
    public UserController(UserService userService,
                          MessageSource messageSource,
                          Validator validator) {
        this.userService = userService;
        this.messageSource = messageSource;
        this.validator = validator;
    }

    @InitBinder(value = {"userRegisterDto", "volunteerRegisterDto"})
    protected void initBinder(WebDataBinder binder) {
        binder.addValidators(new AddressValidator(messageSource));
    }

    @RequestMapping(method = RequestMethod.GET, value = "login")
    public String getLogin() {
        return "redirect:/";
    }

    @RequestMapping(method = RequestMethod.POST, value = "register")
    public ModelAndView postRegisterUser(@ModelAttribute(name = "userRegisterDto") @Validated({Default.class}) UserRegisterDto userRegisterDto,
                             BindingResult result, ModelAndView modelAndView) {
        if (!result.hasErrors()) {
            userService.saveUser(userRegisterDto);
        }
        modelAndView.addObject("contactDto", new ContactDto());
        modelAndView.addObject("userRegisterDto", userRegisterDto);
        modelAndView.addObject("volunteerRegisterDto", new UserRegisterDto());
        modelAndView.addObject("registrationSuccessful", !result.hasErrors());
        modelAndView.setViewName("index");

        return modelAndView;
    }

    @RequestMapping(method = RequestMethod.POST, value = "register/volunteer", consumes = {"multipart/form-data"})
    public ModelAndView postRegisterVolunteer(@ModelAttribute(name = "volunteerRegisterDto") @Validated({Default.class, VolunteerValidation.class})
                                                          UserRegisterDto userRegisterDto,
                                     BindingResult result, ModelAndView modelAndView) {
        if (!result.hasErrors()) {
            userService.saveVolunteer(userRegisterDto);
            return new ModelAndView("redirect:/volunteer/");
        }
        modelAndView.addObject("contactDto", new ContactDto());
        modelAndView.addObject("volunteerRegisterDto", userRegisterDto);
        modelAndView.addObject("userRegisterDto", new UserRegisterDto());
        modelAndView.setViewName("index");

        return modelAndView;
    }

    @GetMapping(value = "terms")
    public String getTerms() {
       return "terms";
    }
}
