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

    private ProfileRepository repository;

    @Autowired
    public ProductController(ProfileRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/prod/{profile}")
    public Mono<List<Product>> products(@PathVariable String profileName) {

        Mono<Profile> profileMono = repository.findByNameOrGetDefault(profileName);
        Logger.debug("Serving products for {}", profileName);
        return profileMono.map(Profile::getProducts);

    }


}
