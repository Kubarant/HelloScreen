package com.hello.screen.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.hello.screen.repository.ReactiveWeatherRepository;
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
