package com.hello.screen.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import com.hello.screen.model.Profile;

import reactor.core.publisher.Mono;

public interface ProfileRepository extends ReactiveCrudRepository<Profile, String>{

	Mono<Profile> findByName(String name);

}
