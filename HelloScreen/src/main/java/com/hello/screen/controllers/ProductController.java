package com.hello.screen.controllers;

import com.hello.screen.model.Product;
import com.hello.screen.model.Profile;
import com.hello.screen.repository.ProfileRepository;
import org.pmw.tinylog.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
public class ProductController {

    private static final String GUEST_PROFILE_NAME = "unknown";
	
	@Autowired
    private
	ProfileRepository repository;
	
	@GetMapping("/prod/{profile}")
    public Mono<List<Product>> prod(@PathVariable String profile) {

        Mono<Profile> profileMono = repository.findByName(profile)
                .log()
                .switchIfEmpty(repository.findByName(GUEST_PROFILE_NAME));
        Logger.debug("Products for {}", profile);
        return profileMono.map(Profile::getProducts);

	}
	
	
}
