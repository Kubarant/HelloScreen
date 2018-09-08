package com.hello.screen.datacollectors.weather;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyExtractors;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class WeatherApiConnector {
    @Value("${weather.api.key}")
    String apiKey;

    public Mono<String> getJson(String url) {
        return WebClient.create(url + apiKey)
                .get()
                .exchange()
                .block()
                .body(BodyExtractors.toMono(String.class))
                .defaultIfEmpty("");
    }
}