package com.hello.screen.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.hello.screen.model.Product;
import com.hello.screen.model.Profile;
import com.hello.screen.repository.ProfileRepository;

import reactor.core.publisher.Mono;

@RestController
public class ProductController {
	
	@Autowired
	ProfileRepository repository;
	
	@GetMapping("/prod/{profile}")
	public Mono<List<Product>> prod(@PathVariable String profile) throws Exception {
		System.out.println(profile);
		return repository.findByName("Kub").map(Profile::getProducts);
		
	}
	
	
}
