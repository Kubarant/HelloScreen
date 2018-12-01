package com.hello.screen.datacollectors.weather;

import com.hello.screen.datacollectors.DataCollector;
import com.hello.screen.model.ForecastWeatherData;
import com.hello.screen.repository.ReactiveForecastWeatherRepository;
import org.pmw.tinylog.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Service
class ForecastWeatherCollector implements DataCollector<ForecastWeatherData> {
    @Value("${weather.forecast.url}")
    private String apiUrl;
    private WeatherApiConnector apiConnector;
    private ReactiveForecastWeatherRepository forecastRepository;
    private WeatherDataMapper dataMapper;

    @Autowired
    public ForecastWeatherCollector(WeatherApiConnector apiConnector, ReactiveForecastWeatherRepository forecastRepository, WeatherDataMapper dataMapper) {
        this.apiConnector = apiConnector;
        this.forecastRepository = forecastRepository;
        this.dataMapper = dataMapper;
    }

    @Scheduled(fixedRate = 1000 * 60 * 30, initialDelay = 2000)
    public void collectAndSave() {
        Mono<String> responseBody = apiConnector.getJson(apiUrl);
        Mono<ForecastWeatherData> forecast = mapToForecastData(responseBody);
        save(forecast);
    }

    private void save(Mono<ForecastWeatherData> forecast) {
        forecast.flatMap(forecastData -> forecastRepository.save(forecastData))
                .subscribe(Logger::info);
    }

    private Mono<ForecastWeatherData> mapToForecastData(Mono<String> responseBody) {
        return responseBody.map(dataMapper::forecastWeatherFromJson)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(ForecastWeatherData::new);
    }

}