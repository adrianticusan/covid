package com.covid19.match.controllers;

import com.covid19.match.dtos.ContactDto;
import com.covid19.match.dtos.UserRegisterDto;
import com.covid19.match.entities.Contact;
import com.covid19.match.services.ContactService;
import com.covid19.match.services.UserService;
import com.covid19.match.validation.groups.VolunteerValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import javax.validation.groups.Default;

@Controller
public class IndexController {
    private final ContactService contactService;

    @Autowired
    public IndexController(ContactService contactService) {
        this.contactService = contactService;
    }

    @RequestMapping
    public ModelAndView getIndex(ModelAndView modelAndView) {
        modelAndView.addObject("contactDto", new ContactDto());
        modelAndView.addObject("userRegisterDto", new UserRegisterDto());
        modelAndView.addObject("volunteerRegisterDto", new UserRegisterDto());
        modelAndView.setViewName("index");
        return modelAndView;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/contact-us")
    public ModelAndView contactUs( @ModelAttribute(name = "contactDto") @Validated({Default.class}) ContactDto contactDto,
                            BindingResult result, ModelAndView modelAndView) {
        if (!result.hasErrors()) {
           contactService.saveMessage(contactDto);
        }
        modelAndView.addObject("contactDto", contactDto);
        modelAndView.addObject("userRegisterDto", new UserRegisterDto());
        modelAndView.addObject("volunteerRegisterDto", new UserRegisterDto());
        modelAndView.setViewName("index");
        return modelAndView;
    }
}
