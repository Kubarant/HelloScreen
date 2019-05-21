package com.hello.screen;


import com.hello.screen.datacollectors.biedronka.BiedronkaImageDownloader;
import com.hello.screen.model.CategoryPreferences;
import com.hello.screen.model.Profile;
import com.hello.screen.repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;
import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Configuration
public class FirstTimeInitializer {
    public static final String GUEST_PROFILE_NAME = "unknown";

    @Value(BiedronkaImageDownloader.IMAGES_DIRECTORY_SPEL)
    private String productsImagesPath;
    private ProfileRepository repository;

    @Autowired
    public FirstTimeInitializer(ProfileRepository repository) {
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
        List<CategoryPreferences> categoryPreferences = Arrays.asList(new CategoryPreferences("All", 16), new CategoryPreferences("Sport", 14),
                new CategoryPreferences("Pol", 14), new CategoryPreferences("Fun", 14),
                new CategoryPreferences("Local", 14), new CategoryPreferences("Economy", 14), new CategoryPreferences("Tech", 14));

        Profile profile = new Profile(GUEST_PROFILE_NAME, Collections.emptyList());
        profile.setPreferredCategories(categoryPreferences);
        return profile;
    }

    public void checkProductImagesDirectoryExist() {
        if (!new File(productsImagesPath).isDirectory()) {
            new File(productsImagesPath).mkdirs();
        }
    }
}
