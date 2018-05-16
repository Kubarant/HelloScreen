package com.hello.screen.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.hello.screen.ProfileRegisterService;
import com.hello.screen.model.ProfileRegistrationDTO;

@RestController
public class ProfileRegistrationController {
	
	@Autowired
	ProfileRegisterService registerService;
	
	@PostMapping("/register")
	public ResponseEntity<?> register(@RequestBody ProfileRegistrationDTO profileRegistrationDTO) {
		
		registerService.register(profileRegistrationDTO);
		System.out.println(profileRegistrationDTO);
		
		return ResponseEntity.ok().build();
	}
	
}
