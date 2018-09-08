package com.hello.screen.datacollectors.weather;

import com.hello.screen.datacollectors.DataCollector;
import com.hello.screen.model.WeatherData;
import com.hello.screen.repository.ReactiveWeatherRepository;
import org.pmw.tinylog.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class WeatherDataCollector implements DataCollector<WeatherData> {
    @Value("${weather.current.url}")
    private String apiUrl;
    private WeatherApiConnector apiConnector;
    private ReactiveWeatherRepository weatherRepository;
    private WeatherDataMapper dataMapper;

    @Autowired
    public WeatherDataCollector(WeatherApiConnector apiConnector, ReactiveWeatherRepository weatherRepository, WeatherDataMapper dataMapper) {
        this.apiConnector = apiConnector;
        this.weatherRepository = weatherRepository;
        this.dataMapper = dataMapper;
    }

    @Scheduled(fixedRate = 1000 * 60 * 30, initialDelay = 2000)
    public List collect() {
        Logger.info("Start collecting current weather");
        Mono<WeatherData> currentWeather = collectCurrentWeather();
        currentWeather.flatMap(data -> weatherRepository.save(data))
                .subscribe();
        Logger.info("Ending collecting current weather");
        return Arrays.asList(currentWeather.block());
    }

    public Mono<WeatherData> collectCurrentWeather() {
        Mono<String> responseBody = apiConnector.getJson(apiUrl);
        return responseBody.map(response -> dataMapper.currentWeatherFromJson(response))
                .filter(Optional::isPresent)
                .map(Optional::get);
    }
}
