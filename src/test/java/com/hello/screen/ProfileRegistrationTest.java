package com.hello.screen;

import com.hello.screen.model.Profile;
import com.hello.screen.model.ProfileRegistrationDTO;
import com.hello.screen.repository.ProfileRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = "app.scheduling.enable=false")
public class ProfileRegistrationTest {
    @Autowired
    ProfileRepository profileRepository;

    @Autowired
    WebTestClient client;

    @Test()
    public void registrationTest() throws InterruptedException {

        ProfileRegistrationDTO profileToRegister = new ProfileRegistrationDTO("Jon", Arrays.asList(), Arrays.asList("abcd", "dcba", "jpga", "fpgj", "jaka"));
        Profile expectedProfile = Profile.registrationDtoToProfile(profileToRegister);

        client.post()
                .uri("/register")
                .body(Mono.just(profileToRegister), ProfileRegistrationDTO.class)
                .exchange()
                .expectStatus()
                .isOk();

        Mono<Profile> savedProfile = profileRepository.findByName(expectedProfile.getName());
        assertEquals(savedProfile.block(), expectedProfile);

    }

}
