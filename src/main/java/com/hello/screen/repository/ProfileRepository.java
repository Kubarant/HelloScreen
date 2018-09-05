package com.hello.screen.repository;

import com.hello.screen.model.Profile;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface ProfileRepository extends ReactiveCrudRepository<Profile, String> {
    String GUEST_PROFILE_NAME = "UNKNOWN";

    Mono<Profile> findByName(String name);


    default Mono<Profile> findByNameOrGetDefault(String name) {
        Mono<Profile> guestUser = findByName(GUEST_PROFILE_NAME);
        return findByName(name).switchIfEmpty(guestUser);
    }

}
