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

    @Autowired
    private
    ProfileRepository repos;

    @GetMapping("/news/{profile}")
    public Mono<List<News>> news(@PathVariable String profile) {
        Logger.debug("News for {}", profile);
        return repos.findByName(profile)
                .map(Profile::getNews);
    }

}
