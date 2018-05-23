package com.hello.screen.datacollectors.weather;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.reactive.function.BodyExtractors;
import org.springframework.web.reactive.function.client.WebClient;

import com.hello.screen.model.ForecastWeatherData;
import com.hello.screen.model.WeatherData;
import com.hello.screen.repository.ReactiveForecastWeatherRepository;
import com.hello.screen.repository.ReactiveWeatherRepository;

import reactor.core.publisher.Mono;

@Configuration
public class WeatherDataCollector {

	@Autowired
	ReactiveWeatherRepository weatherRepository;

	@Autowired
	ReactiveForecastWeatherRepository forecastRepository;

	@Autowired
	WeatherDataMapper dataMapper;

	final String appKey = "d5ad5b22d5ed61d52a07fbeea6ff1cbf";

	@Scheduled(fixedRate = 1000 * 60 * 30, initialDelay = 2000)
	public void collectAndSave() {
		System.out.println("eyyys ");
		Mono<WeatherData> currentWeather = collectCurrentWeather();
		currentWeather.subscribe(data -> weatherRepository.save(data).subscribe());
	}

	public Mono<WeatherData> collectCurrentWeather() {
		String apiUrl = "http://api.openweathermap.org/data/2.5/weather?q=Lubaczow&appid=";
		Mono<String> responseBody = getResponseBody(apiUrl + appKey);

		Mono<WeatherData> weatherData = 
				responseBody.map(response -> dataMapper.currentWeatherFromJson(response))
				.filter(opData -> opData.isPresent())
				.map(Optional::get);
		return weatherData;

	}

	@Scheduled(fixedRate = 1000 * 60 * 30, initialDelay = 2000)
	public void collectForecastWeather() {
		String apiUrl = "http://api.openweathermap.org/data/2.5/forecast?q=Lubaczow&appid=";
		Mono<String> responseBody = getResponseBody(apiUrl + appKey);
		Mono<ForecastWeatherData> forecast = responseBody.map(dataMapper::forecastWeatherFromJson)
				.filter(Optional::isPresent).map(Optional::get).map(ForecastWeatherData::new);
		forecast.subscribe(forecastData -> forecastRepository.save(forecastData).subscribe(System.out::println));

	}

	public Mono<String> getResponseBody(String url) {
		return WebClient.create(url).get().exchange().block().body(BodyExtractors.toMono(String.class))
				.defaultIfEmpty("");

	}

}
