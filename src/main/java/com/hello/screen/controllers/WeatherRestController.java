package com.hello.screen.controllers;

import com.hello.screen.model.ForecastWeatherData;
import com.hello.screen.model.WeatherData;
import com.hello.screen.repository.ReactiveForecastWeatherRepository;
import com.hello.screen.repository.ReactiveWeatherRepository;
import org.pmw.tinylog.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
public class WeatherRestController {

    private final ReactiveWeatherRepository weatherRepository;
    private final ReactiveForecastWeatherRepository forecastRepository;
    private final MongoTemplate template;

    @Autowired
    public WeatherRestController(ReactiveWeatherRepository weatherRepository, ReactiveForecastWeatherRepository forecastRepository, MongoTemplate template) {
        this.weatherRepository = weatherRepository;
        this.forecastRepository = forecastRepository;
        this.template = template;
    }

    @GetMapping("/sun")
    public Mono<WeatherData> currentWeather() {
        Query query = new Query();
        query.limit(1);
        query.with(new Sort(Sort.Direction.DESC, "creation"));
        List<WeatherData> find = template.find(query, WeatherData.class);
        Logger.debug("Current weather response {}", find);

        return Mono.just(find.get(0));
    }

    @GetMapping("/forecast")
    public Mono<ForecastWeatherData> forecastWeather() {
        Query query = new Query();
        query.limit(1);
        query.with(new Sort(Sort.Direction.DESC, "creation"));
        List<ForecastWeatherData> find = template.find(query, ForecastWeatherData.class);
        Logger.debug("Current weather response {}", find);

        return Mono.just(find.get(0));
    }

}
