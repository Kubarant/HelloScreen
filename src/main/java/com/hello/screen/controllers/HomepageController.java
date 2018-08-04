package com.hello.screen.controllers;

import com.hello.screen.repository.ReactiveWeatherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomepageController {

    @Autowired
    ReactiveWeatherRepository weatherRepository;


    @GetMapping("/ho")
    public String hom() {


        return "index";

    }

    @GetMapping("/profile")
    public String profile() {


        return "profile";

    }
}
