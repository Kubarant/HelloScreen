package com.hello.screen.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.hello.screen.model.News;
import com.hello.screen.model.Profile;
import com.hello.screen.repository.ProfileRepository;

import reactor.core.publisher.Mono;

@RestController
public class NewsRestController {
	
	@Autowired
	ProfileRepository repos;
	
	@GetMapping("/news/{profile}")
	public Mono<List<News>> news(@PathVariable String profile){
		System.out.println("Newsy "+profile);
		
		return repos.findByName(profile).map(Profile::getNews);
	}

}
