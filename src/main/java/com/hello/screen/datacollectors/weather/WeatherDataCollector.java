package com.hello.screen.datacollectors.weather;

import com.hello.screen.model.ForecastWeatherData;
import com.hello.screen.model.WeatherData;
import com.hello.screen.repository.ReactiveForecastWeatherRepository;
import com.hello.screen.repository.ReactiveWeatherRepository;
import org.pmw.tinylog.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.reactive.function.BodyExtractors;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Configuration
public class WeatherDataCollector {

    private final String appKey = "d5ad5b22d5ed61d52a07fbeea6ff1cbf";
    @Autowired
    private ReactiveWeatherRepository weatherRepository;
    @Autowired
    private ReactiveForecastWeatherRepository forecastRepository;
    @Autowired
    private WeatherDataMapper dataMapper;

    //@Scheduled(fixedRate = 1000 * 60 * 30, initialDelay = 2000)
    public void collectAndSave() {
        Logger.info("Start collecting current weather");
        Mono<WeatherData> currentWeather = collectCurrentWeather();
        currentWeather.subscribe(data -> weatherRepository.save(data)
                .subscribe());
        Logger.info("Ending collecting current weather");

    }

    public Mono<WeatherData> collectCurrentWeather() {
        String apiUrl = "http://api.openweathermap.org/data/2.5/weather?q=Lubaczow&appid=";
        Mono<String> responseBody = getResponseBody(apiUrl + appKey);


        return responseBody.map(response -> dataMapper.currentWeatherFromJson(response))
                .filter(Optional::isPresent)
                .map(Optional::get);


    }

    @Scheduled(fixedRate = 1000 * 60 * 30, initialDelay = 2000)
    public void collectForecastWeather() {
        String apiUrl = "http://api.openweathermap.org/data/2.5/forecast?q=Lubaczow&appid=";
        Mono<String> responseBody = getResponseBody(apiUrl + appKey);
        Mono<ForecastWeatherData> forecast = responseBody.map(dataMapper::forecastWeatherFromJson)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(ForecastWeatherData::new);
        forecast.subscribe(forecastData -> forecastRepository.save(forecastData)
                .subscribe(System.out::println));

    }

    private Mono<String> getResponseBody(String url) {
        return WebClient.create(url)
                .get()
                .exchange()
                .block()
                .body(BodyExtractors.toMono(String.class))
                .defaultIfEmpty("");

    }

}
