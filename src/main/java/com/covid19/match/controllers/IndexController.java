package com.covid19.match.controllers;

import com.covid19.match.dtos.UserRegisterDto;
import org.dom4j.rule.Mode;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class IndexController {

    @RequestMapping
    public ModelAndView getIndex(ModelAndView modelAndView) {
        modelAndView.addObject("userRegisterDto", new UserRegisterDto());
        modelAndView.addObject("volunteerRegisterDto", new UserRegisterDto());
        modelAndView.setViewName("index");
        return modelAndView;
    }
}
