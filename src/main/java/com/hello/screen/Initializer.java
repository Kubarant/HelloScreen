package com.hello.screen;


import com.hello.screen.datacollectors.biedronka.BiedronkaImageDownloader;
import com.hello.screen.model.Profile;
import com.hello.screen.repository.ProfileRepository;
import io.vavr.control.Try;
import org.pmw.tinylog.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;
import java.nio.file.Paths;
import java.util.Collections;

@Configuration
public class Initializer {
    public static final String GUEST_PROFILE_NAME = "unknown";

    @Value(BiedronkaImageDownloader.IMAGES_DIRECTORY_SPEL)
    private String productsImagesPath;
    private ProfileRepository repository;

    @Autowired
    public Initializer(ProfileRepository repository) {
        this.repository = repository;
    }

    @PostConstruct
    public void init() {
        saveGuestProfileIfNotExist();
        checkProductImagesDirectoryExist();
    }

    public void saveGuestProfileIfNotExist() {
        Mono<Profile> profileMono = repository.findByName(GUEST_PROFILE_NAME)
                .switchIfEmpty(Mono.just(createGuestProfile()));

        repository.saveAll(profileMono)
                .subscribe();
    }

    public Profile createGuestProfile() {
        return new Profile(GUEST_PROFILE_NAME, Collections.emptyList());
    }

    public void checkProductImagesDirectoryExist() {
        Try.of(() -> Paths.get(productsImagesPath))
                .onFailure(Logger::error)
                .filter(path -> !path.toFile()
                        .isDirectory())
                .andThenTry(path -> path.toFile()
                        .createNewFile())
                .onFailure(Logger::error);
    }
}
