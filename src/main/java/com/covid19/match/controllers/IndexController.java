package com.covid19.match.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

    @RequestMapping
    public String getIndex() {
        return "index";
    }

    @RequestMapping(value = "/design")
    public String getDesign() {
        return "design";
    }
}
