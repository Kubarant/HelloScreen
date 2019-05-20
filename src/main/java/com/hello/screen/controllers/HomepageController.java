package com.hello.screen.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomepageController {

    @GetMapping({"", "home", "/home/*"})
    public String homepage() {
        return "index";
    }

    @GetMapping("/register")
    public String registerPage() {
        return "profile";

    }
}
