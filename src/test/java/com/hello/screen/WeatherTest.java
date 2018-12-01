package com.hello.screen;

import com.hello.screen.datacollectors.weather.WeatherDataCollector;
import com.hello.screen.model.WeatherData;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = "app.scheduling.enable=false")
public class WeatherTest {

    @Autowired
    WebTestClient webClient;

    @Autowired
    WeatherDataCollector weatherCollector;

    @Test
    public void testIfEndpointServeProperData() {
        weatherCollector.collectAndSave();

        WeatherData currentWeather = weatherCollector.collectCurrentWeather()
                .block();
        webClient.get()
                .uri("/sun")
                .exchange()
                .expectBody(WeatherData.class)
                .isEqualTo(currentWeather);


    }

}
