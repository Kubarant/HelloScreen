package com.hello.screen.controllers;

import com.hello.screen.model.News;
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
public class NewsRestController {

    private ProfileRepository repos;

    @Autowired
    public NewsRestController(ProfileRepository repos) {
        this.repos = repos;
    }

    @GetMapping("/news/{profileName}")
    public Mono<List<News>> news(@PathVariable String profileName) {
        Logger.debug("News for {}", profileName);
        return repos.findByNameOrGetDefault(profileName)
                .map(Profile::getNews);
    }

}
